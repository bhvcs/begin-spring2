package spring;

import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Setter
public class ChangePasswordService {
    private MemberDao memberDao; //@setter로 의존하는 memerDao객체를 주입받는다

    @Transactional
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email);

        if (member == null) {
            System.out.println("membernotfoundexception");
            throw new MemberNotFoundException();
        }
        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);


    }
}
