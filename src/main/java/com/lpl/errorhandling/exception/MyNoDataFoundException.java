package com.lpl.errorhandling.exception;

public class MyNoDataFoundException extends MyAbstractException {

    public MyNoDataFoundException() {
        super(15, "No data found");
    }
}
