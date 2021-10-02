package com.lpl.errorhandling.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class LplAbstractException extends RuntimeException {
    private final Integer errorCode;
    private final String description;

}
