package com.lpl.errorhandling.exception;

import lombok.Getter;

@Getter
public class LplInvalidParameterException extends LplAbstractException {
    private final static int errorCode = 11234;
    private final String field;
    private final String value;

    public LplInvalidParameterException(String field, String value, String description) {
        super(errorCode, description);
        this.field = field;
        this.value = value;
    }

    public LplInvalidParameterException(String field) {
        super(errorCode, "Null parameter");
        this.field = field;
        this.value = null;
    }
}
