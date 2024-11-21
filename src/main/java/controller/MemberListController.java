package controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.Member;
import spring.MemberDao;

import java.util.List;


@Setter
@Controller
public class MemberListController {
    private MemberDao memberDao;

    @RequestMapping("/members")
    public String list(@ModelAttribute("cmd") ListCommand listCommand, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "member/memberList";
        }
        if (listCommand.getFrom() != null && listCommand.getTo() != null) {
            List<Member> members = memberDao.selectByRegdate(listCommand.getFrom(), listCommand.getTo());
            model.addAttribute("members", members);
        }
        return "member/memberList";//어찌보면 새로고침인건가, 한 화면에서 보여주는 기능일 때 이렇게 하면 좋겠네
    }
}
