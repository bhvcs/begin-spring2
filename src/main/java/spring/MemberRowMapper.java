package spring;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {//Member 클래스를 위한 RowMapper임, 공통으로 쓰여서, 코드 중복을 피하기 위해 클래스를 정의한다
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member(
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getString("NAME"),
                rs.getTimestamp("REGDATE").toLocalDateTime()
        );
        member.setId(rs.getLong("ID"));
        return member;
    }

}
