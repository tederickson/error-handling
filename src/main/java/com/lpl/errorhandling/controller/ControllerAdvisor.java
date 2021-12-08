package com.lpl.errorhandling.controller;

import com.lpl.errorhandling.domain.LplBadParameterError;
import com.lpl.errorhandling.domain.LplGenericError;
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
    LplGenericError notFoundHandler(com.lpl.errorhandling.exception.MyAbstractException ex) {
        return LplGenericError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(com.lpl.errorhandling.exception.MyInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    LplBadParameterError invalidParameterHandler(com.lpl.errorhandling.exception.MyInvalidParameterException ex) {
        return LplBadParameterError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .field(ex.getField())
                .value(ex.getValue())
                .build();
    }
}
