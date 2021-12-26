package com.example.demo1.exception;

import com.example.demo1.constants.ResultCode;
import com.example.demo1.utils.CommonException;
import com.example.demo1.utils.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArguemntNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.OK).body(ResultJson.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    /**
     * 权限不足
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity handleInsufficientAuthenticationException(InsufficientAuthenticationException e,
                                                                    HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录或该账号没有权限");
    }


    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(CommonException e,
                                                HttpServletRequest request) {
        e.printStackTrace();
        System.out.println(e.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(ResultJson.failure(ResultCode.SERVER_ERROR, e.getMessage()));
    }

}
