package com.lpl.errorhandling.controller;

import com.lpl.errorhandling.domain.MyBadParameterError;
import com.lpl.errorhandling.domain.MyGenericError;
import com.lpl.errorhandling.exception.MyAbstractException;
import com.lpl.errorhandling.exception.MyInvalidParameterException;
import com.lpl.errorhandling.exception.MyNoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseBody
    @ExceptionHandler({com.lpl.errorhandling.exception.MyCityNotFoundException.class, MyNoDataFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MyGenericError notFoundHandler(MyAbstractException ex) {
        return com.lpl.errorhandling.domain.MyGenericError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(com.lpl.errorhandling.exception.MyInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    MyBadParameterError invalidParameterHandler(MyInvalidParameterException ex) {
        return com.lpl.errorhandling.domain.MyBadParameterError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .field(ex.getField())
                .value(ex.getValue())
                .build();
    }
}
