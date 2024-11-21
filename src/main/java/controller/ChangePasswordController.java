package controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.ChangePasswordService;
import spring.WrongIdPasswordException;

import javax.servlet.http.HttpSession;

@Setter
@Controller
@RequestMapping("/edit/changePassword")
public class ChangePasswordController {
    private ChangePasswordService changePasswordService;//@Setter

    @GetMapping
    public String form(@ModelAttribute("command") ChangePasswordCommand pwdCmd, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        if (authInfo == null) {
            return "redirect:/login";
        }
        return "edit/changePasswordForm";
    }

    @PostMapping
    public String submit(@ModelAttribute("command") ChangePasswordCommand pwdCmd, Errors errors, HttpSession session) {
        new ChangePasswordCommandValidator().validate(pwdCmd, errors);//model의 command속성에 pwdCmd 객체를 넣겠다
        if (errors.hasErrors()) {
            return "edit/changePasswordForm";
        }
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        try {
            changePasswordService.changePassword(
                    authInfo.getEmail(),
                    pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword()
            );
            return "edit/changedPassword";
        } catch (WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching");
            return "edit/changePasswordForm";
        }
    }
}
