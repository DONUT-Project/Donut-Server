package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class UserNotFoundException extends DonutException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
