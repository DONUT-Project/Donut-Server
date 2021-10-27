package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class LoginFailedException extends DonutException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
}
