package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class RefreshTokenException extends DonutException {
    public RefreshTokenException() {
        super(ErrorCode.REFRESH_TOKEN_FAILED);
    }
}
