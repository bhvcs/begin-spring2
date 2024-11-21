package controller;

import lombok.Setter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;

@Setter
@Controller
public class MemberDetailController {
    private MemberDao memberDao;

    @GetMapping("/members/{id}")// {경로 변수}
    public String detail(@PathVariable("id") Long memId, Model model) {
        Member member = memberDao.selectById(memId);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        model.addAttribute("member", member);
        return "member/memberDetail";
    }

    //같은 컨트롤러에 @ExceptionHandler를 적용한 메서드가 있다면 그 메서드가 해당 컨트롤러에서 발생한 익셉션을 직접 처리한다
    @ExceptionHandler(TypeMismatchException.class)//id의 데이터 타입인 Long으롭 변환할 수 없을 때
    public String handleTypeMismatchException() {
        return "member/invalidId";
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public String handleMemberNotFoundException() {
        return "member/noMember";
    }
}