package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class BadRequestException extends DonutException {
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}
