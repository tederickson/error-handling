package com.lpl.errorhandling.exception;

public class LplNoDataFoundException extends LplAbstractException {

    public LplNoDataFoundException() {
        super(15, "No data found");
    }
}
