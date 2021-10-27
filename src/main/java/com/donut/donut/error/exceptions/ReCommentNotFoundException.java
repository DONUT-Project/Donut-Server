package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class ReCommentNotFoundException extends DonutException {
    public ReCommentNotFoundException() {
        super(ErrorCode.RECOMMENT_NOT_FOUND);
    }
}
