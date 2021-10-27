package com.donut.donut.error.exception;

import lombok.Getter;

@Getter
public class DonutException extends RuntimeException {

    private final ErrorCode errorCode;

    public DonutException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DonutException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
