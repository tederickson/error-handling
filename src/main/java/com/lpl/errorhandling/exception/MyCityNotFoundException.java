package com.lpl.errorhandling.exception;

public class MyCityNotFoundException extends MyAbstractException {

    public MyCityNotFoundException(Long id) {
        super(111, String.format("City with Id %d not found", id));
    }
}
