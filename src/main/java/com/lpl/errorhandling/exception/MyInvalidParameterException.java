package com.lpl.errorhandling.exception;

import lombok.Getter;

@Getter
public class MyInvalidParameterException extends MyAbstractException {
    private final static int errorCode = 11234;
    private final String field;
    private final String value;

    public MyInvalidParameterException(String field, String value, String description) {
        super(errorCode, description);
        this.field = field;
        this.value = value;
    }

    public MyInvalidParameterException(String field) {
        super(errorCode, "Null parameter");
        this.field = field;
        this.value = null;
    }
}
