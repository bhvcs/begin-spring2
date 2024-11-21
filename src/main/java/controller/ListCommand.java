package controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListCommand {

    @DateTimeFormat(pattern = "yyyyMMddHH")//이 형식을 이용해서 LocalDateTime으로 변환해준다
    private LocalDateTime from;
    @DateTimeFormat(pattern = "yyyyMMddHH")
    private LocalDateTime to;

}
