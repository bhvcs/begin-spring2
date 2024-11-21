package controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordCommand {
    private String currentPassword;
    private String newPassword;

}
