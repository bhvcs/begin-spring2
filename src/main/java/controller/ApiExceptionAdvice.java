package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.MemberNotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice("controller")//@RestControllerAdvice를 사용하면 에러 관리 코드를 한 곳에서 관리 가능
public class ApiExceptionAdvice {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDate() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("no member"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException ex) {
        String errorCodes = ex.getBindingResult().getAllErrors() //ex.getBindingResult() -> Errors
                .stream()
                .map(error -> error.getCodes()[0])
                .collect(Collectors.joining(","));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("errorCodes = " + errorCodes));
    }
}
