package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class AlreadyFriendException extends DonutException {
    public AlreadyFriendException() {
        super(ErrorCode.ALREADY_FRIEND);
    }
}
