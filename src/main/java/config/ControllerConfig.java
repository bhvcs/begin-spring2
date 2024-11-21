package config;

import controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;
import survey.SurveyController;

@Configuration
public class ControllerConfig {

    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ChangePasswordService changePasswordService;
    @Autowired
    private MemberDao memberDao;

    @Bean
    public RegisterController registerController() {
        RegisterController registerController = new RegisterController();
        registerController.setMemberRegisterService(memberRegisterService);
        return registerController;
    }

    @Bean
    public SurveyController surveyController() {
        return new SurveyController();
    }

    @Bean
    public LoginController loginController() {//컨트롤러에 서비스를 넣어서 진행하는 방식 기억하자.
        LoginController loginController = new LoginController();
        loginController.setAuthService(authService);
        return loginController;
    }

    @Bean
    public LogoutController logoutController() {
        return new LogoutController();
    }

    @Bean
    public ChangePasswordController changePasswordController() {
        ChangePasswordController changePasswordController = new ChangePasswordController();
        changePasswordController.setChangePasswordService(changePasswordService);
        return changePasswordController;
    }

    @Bean
    public MemberListController memberListController() {
        MemberListController memberListController = new MemberListController();
        memberListController.setMemberDao(memberDao);
        return memberListController;
    }

    @Bean
    public MemberDetailController memberDetailController() {
        MemberDetailController memberDetailController = new MemberDetailController();
        memberDetailController.setMemberDao(memberDao);
        return memberDetailController;
    }

    @Bean
    public RestMemberController restMemberController() {
        RestMemberController restMemberController = new RestMemberController();
        restMemberController.setMemberDao(memberDao);
        restMemberController.setMemberRegisterService(memberRegisterService);
        return restMemberController;
    }
}
