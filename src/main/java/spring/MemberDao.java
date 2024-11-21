package spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDao {//memberDao의 map이 현재 db역할을 해주네

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Member> memRowMapper = new RowMapper<Member>() {//임의의 객체를 바로 만들기
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
    };
    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query("select * from MEMBER where EMAIL = ?", new MemberRowMapper(),
//                new RowMapper<Member>() {//임의의 클래스!, (my-interface는 원래 생성자 못만드는데 이런식으로 추상 메서드 구현하면서 하려는 형식인 것으로 보임)
//                    @Override //만약 이 클래스가 중복되어 사용된다면 지금은 임의의 클래스로 구현중이지만 따로 클래스를 하나 만들어서 할 수 있다
//                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {//람다 형식으로 하면 더 간단함
//                        Member member = new Member(
//                                rs.getString("EMAIL"),
//                                rs.getString("PASSWORD"),
//                                rs.getString("NAME"),
//                                rs.getTimestamp("REGDATE").toLocalDateTime()
//                        );
//                        member.setId(rs.getLong("ID"));
//                        return member;
//                    }
//                }, //원래는 이렇게 임의의 클래스를 만들어서 해야되는데, selectAll에서도 중복되니 MemberRowMapper 클래스를 따로 하나 만들어서 구현한것이다.
                email);
        return results.isEmpty() ? null : results.get(0);
    }

    public void insert(final Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();//테이블에서 자동 생성되는 key값을 알기 위한 녀석.
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
                                "values (?, ?, ?, ?)",
                        new String[]{"ID"});
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());
    }

    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail()
        );
    }

    public List<Member> selectAll(){
        List<Member> results = jdbcTemplate.query("select * from MEMBER", new MemberRowMapper()
//                new RowMapper<Member>(){
//                    @Override
//                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        Member member = new Member(
//                                rs.getString("EMAIL"),
//                                rs.getString("PASSWORD"),
//                                rs.getString("NAME"),
//                                rs.getTimestamp("REGDATE").toLocalDateTime()
//                        );
//                        member.setId(rs.getLong("ID"));
//                        return member;
//                    }
//    }
        );
        return results;
    }

    public int count() {
        Integer count = jdbcTemplate.queryForObject( //쿼리 실행 결과가 행이 한 개인 경우에 사용 가능하다. 행이 딱 한개가 아니라면 query() 메서드를 사용해야 함.
                "select count(*) from MEMBER", Integer.class);
        return count;
    }

    public List<Member> selectByRegdate(LocalDateTime from, LocalDateTime to) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where REGDATE between ? and ? " +
                        "order by REGDATE desc",
                memRowMapper,
                from, to);
        return results;
    }

    public Member selectById(Long memId) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where ID = ?",
                memRowMapper, memId
        );
        return results.isEmpty() ? null : results.get(0);
    }
}
