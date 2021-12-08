package com.lpl.errorhandling.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseBody
    @ExceptionHandler({com.lpl.errorhandling.exception.MyCityNotFoundException.class, com.lpl.errorhandling.exception.MyNoDataFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    com.lpl.errorhandling.domain.MyGenericError notFoundHandler(com.lpl.errorhandling.exception.MyAbstractException ex) {
        return com.lpl.errorhandling.domain.MyGenericError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(com.lpl.errorhandling.exception.MyInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    com.lpl.errorhandling.domain.MyBadParameterError invalidParameterHandler(com.lpl.errorhandling.exception.MyInvalidParameterException ex) {
        return com.lpl.errorhandling.domain.MyBadParameterError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .field(ex.getField())
                .value(ex.getValue())
                .build();
    }
}
