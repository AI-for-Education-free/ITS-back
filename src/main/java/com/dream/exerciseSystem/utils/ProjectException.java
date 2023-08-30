package com.dream.exerciseSystem.utils;


import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 异常处理器
// 基于RestControllerAdvice的异常拦截只能捕获请求达controller之后的程序异常
@RestControllerAdvice
public class ProjectException {
    @ExceptionHandler(NonexistentUserException.class)
    public DataWrapper doNonexistentUserException(NonexistentUserException exception) {
        // 记录日志、通知运维、通知开发
//        exception.printStackTrace();
        return new DataWrapper(false).msgBuilder(exception.getMessage());
    }

    @ExceptionHandler(PasswordErrorException.class)
    public DataWrapper doPasswordErrorException(PasswordErrorException exception) {
//        exception.printStackTrace();
        return new DataWrapper(false).msgBuilder(exception.getMessage());
    }
}
