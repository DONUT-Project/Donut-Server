package com.donut.donut.error.exceptions;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;

public class DoneNotFoundException extends DonutException {
    public DoneNotFoundException() {
        super(ErrorCode.DONE_NOT_FOUND);
    }
}
