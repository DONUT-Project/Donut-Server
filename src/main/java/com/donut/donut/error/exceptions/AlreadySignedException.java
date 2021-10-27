package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class AlreadySignedException extends DonutException {
    public AlreadySignedException() {
        super(ErrorCode.ALREADY_USER_SIGNED);
    }
}
