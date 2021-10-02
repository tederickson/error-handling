package com.lpl.errorhandling.exception;

public class LplCityNotFoundException extends LplAbstractException {

    public LplCityNotFoundException(Long id) {
        super(111, String.format("City with Id %d not found", id));
    }
}
