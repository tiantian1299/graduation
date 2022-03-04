package com.example.office.wx.config;

import com.example.office.wx.exception.OfficeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理异常信息
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        log.error("执行异常", e);
        if (e instanceof MethodArgumentNotValidException) {
            //后端校验异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            //某个字段不通过验证，原因是什么
            return exception.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof OfficeException) {
            OfficeException exception = (OfficeException) e;
            return exception.getMsg();
        } else if (e instanceof UnauthorizedException) {
            return "你不具备相关权限";
        } else {
            return "后端执行异常";
        }
    }
}
