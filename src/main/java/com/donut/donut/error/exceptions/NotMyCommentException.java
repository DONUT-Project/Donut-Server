package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class NotMyCommentException extends DonutException {
    public NotMyCommentException() {
        super(ErrorCode.NOT_MY_COMMENT);
    }
}
