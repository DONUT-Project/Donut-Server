package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class FriendNotFoundException extends DonutException {
    public FriendNotFoundException() {
        super(ErrorCode.FRIEND_NOT_FOUND);
    }
}
