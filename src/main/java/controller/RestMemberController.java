package controller;

import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import spring.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@RestController
public class RestMemberController {
    private MemberDao memberDao;
    private MemberRegisterService memberRegisterService;

    @GetMapping("/api/members")
    public List<Member> members() {
        return memberDao.selectAll();
    }

    //    @PostMapping("/api/members")
//    public void newMember(@RequestBody @Valid RegisterRequest regReq,
//                          HttpServletResponse response) throws IOException {
//        try {
//            Long newMemberId = memberRegisterService.regist(regReq);
//            response.setHeader("Location", "/api/members/" + newMemberId);
//            response.setStatus(HttpServletResponse.SC_CREATED);
//        } catch (DuplicateMemberException dupEx) {
//            response.sendError(HttpServletResponse.SC_CONFLICT);
//        }
//    }
    @PostMapping("/api/members")
    public ResponseEntity<Object> newMember(@RequestBody @Valid RegisterRequest regReq, Errors errors) {
        if (errors.hasErrors()) {
            String errorCodes = errors.getAllErrors()
                    .stream()
                    .map(error -> error.getCodes()[0])
                    .collect(Collectors.joining(","));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("errorCodes = " + errorCodes));
        }
        try {
            Long newMemberId = memberRegisterService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build();
        } catch (DuplicateMemberException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

//    @GetMapping("/api/members/{id}")
//    public ResponseEntity<Object> member(@PathVariable Long id) {
//        Member member = memberDao.selectById(id);
//        if (member == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorResponse("no member"));
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(member);
//    }
    @GetMapping("/api/members/{id}")
    public Member member(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Member member = memberDao.selectById(id);
        if (member == null) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return null;
            throw new MemberNotFoundException();
        }
        return member;
    }
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoData(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("no member"));
    }
}
