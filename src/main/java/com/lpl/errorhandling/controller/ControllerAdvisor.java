package com.lpl.errorhandling.controller;

import com.lpl.errorhandling.domain.LplBadParameterError;
import com.lpl.errorhandling.domain.LplGenericError;
import com.lpl.errorhandling.exception.LplAbstractException;
import com.lpl.errorhandling.exception.LplCityNotFoundException;
import com.lpl.errorhandling.exception.LplInvalidParameterException;
import com.lpl.errorhandling.exception.LplNoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseBody
    @ExceptionHandler({LplCityNotFoundException.class, LplNoDataFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    LplGenericError notFoundHandler(LplAbstractException ex) {
        return LplGenericError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(LplInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    LplBadParameterError invalidParameterHandler(LplInvalidParameterException ex) {
        return LplBadParameterError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ex.getErrorCode())
                .statusMessage(ex.getDescription())
                .field(ex.getField())
                .value(ex.getValue())
                .build();
    }
}
