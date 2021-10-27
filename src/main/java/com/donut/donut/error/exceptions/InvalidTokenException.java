package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class InvalidTokenException extends DonutException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
