package com.rental.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserAccessDeniedException.class)
    String handleUserAccessDeniedException(UserAccessDeniedException e){
        return "error/403";
    }


}
