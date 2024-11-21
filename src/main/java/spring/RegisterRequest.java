package spring;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Size;



@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Email// Bean Validation과 프로바이더가 제공하는 애노테이션
    private String email;
    @Size(min = 6)
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty
    private String name;

    public boolean isPasswordEqualToConfirmPassword(){
        return password.equals(confirmPassword);
    }
}
