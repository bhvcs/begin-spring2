package spring;

import lombok.Getter;

@Getter
public class AuthInfo {
    private Long id;
    private String email;
    private String name;

    public AuthInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }


}
