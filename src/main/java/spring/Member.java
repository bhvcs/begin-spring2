package spring;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String email;
    @JsonIgnore //JSON 응답에 포함시키지 않겠다는 애노테이션
    private String password;
    private String name;
//    @JsonFormat(shape= JsonFormat.Shape.STRING) 날짜 형식을 ISO-8601형식으로
//    @JsonFormat(pattern = "yyyyMMddHHmmss")//pattern 속성은 java.time.format.DateTimeFormatter 클래스나 java.text.Simple.DateFormat 클래스의 API 문서에 정의된 패턴을 따라야 한다
    private LocalDateTime registerDateTime; //MvcConfig에 converter을 추가하였다

    public Member(String email, String password, String name, LocalDateTime registerDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDateTime = registerDateTime;
    }

    void setId(Long id) {
        this.id = id;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            throw new WrongIdPasswordException();
        }
        this.password = newPassword;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
