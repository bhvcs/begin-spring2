package controller;


//import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;


@Setter
@Controller
public class RegisterController {

//    @Autowired
    private MemberRegisterService memberRegisterService;

    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "register/step1";
    }

    @PostMapping("/register/step2")
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, RegisterRequest registerRequest) {
        if (!agree) {
            return "register/step1";
        }
//        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step2";//form:form 태그를 쓰려면 커맨드 객체가 있어야함
    }

    @GetMapping("/register/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @PostMapping("/register/step3")
    public String handleStep3(@Validated RegisterRequest regReq, Errors errors) {//RegisterRequest는 커맨드 객체에 접근할 속성 이름임
        //new RegisterRequestValidator().validate(regReq, errors);
        if (errors.hasErrors()) {
            System.out.println("errors.hasErrors");
            return "register/step2";//다시 이 주소로 왔을 때는, errors 객체에 오류가 있잖아 -> 오류가 있다면 이걸 이용해서 띄우는겨
        }
        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex) {
            return "register/step2";
        }
    }

//    @InitBinder//컨트롤러 Validator을 설정할 때 사용
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(new RegisterRequestValidator());//컨트롤러 validator만으로 검증하고 싶을 때, addValidator는 글로벌 validator, 컨트롤러 validator 순으로 진행됨
//    }
}
