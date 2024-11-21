package controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@Setter
public class LoginController {
    private AuthService authService;//@Setter

    @GetMapping
    public String form(LoginCommand loginCommand, @CookieValue(value = "REMEMBER", required = false) Cookie rCookie) {
        if (rCookie != null) {
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }

    @PostMapping
    public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, HttpServletResponse response) {
        new LoginCommandValidator().validate(loginCommand, errors);
        if (errors.hasErrors()) {//로그인 형식이 잘못 되었을 때
            return "login/loginForm";
        }
        try {
            AuthInfo authInfo = authService.authenticate(
                    loginCommand.getEmail(),
                    loginCommand.getPassword()
            );
            session.setAttribute("authInfo", authInfo); //TODO: 세션에 authInfo 저장해야 함

            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
            rememberCookie.setPath("/");
            if (loginCommand.isRememberEmail()) {
                rememberCookie.setMaxAge(60 * 60 * 24 * 30);
            }else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);

            return "login/loginSuccess";
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";
        }
    }
}
