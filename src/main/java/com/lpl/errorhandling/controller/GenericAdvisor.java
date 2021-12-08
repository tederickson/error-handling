package com.lpl.errorhandling.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class GenericAdvisor {
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    com.lpl.errorhandling.domain.MyGenericError noDataFoundHandler(NullPointerException ex) {
        log.error("Create high priority Story to fix this", ex);
        return com.lpl.errorhandling.domain.MyGenericError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusMessage(ex.getMessage())
                .build();
    }
}
