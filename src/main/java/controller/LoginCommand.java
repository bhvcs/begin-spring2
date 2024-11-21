package controller;

import lombok.Setter;

@Setter
public class LoginCommand {
    private String email;
    private String password;
    private boolean rememberEmail;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberEmail() {
        return rememberEmail;
    }
}
