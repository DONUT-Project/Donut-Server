package com.donut.donut.error;

import com.donut.donut.error.exception.DonutException;
import com.donut.donut.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DonutExceptionHandler {

    @ExceptionHandler(DonutException.class)
    protected ResponseEntity<ErrorResponse> handlerExceptions(final DonutException e) {
        log.error(e.getMessage());

        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getCode()));
    }
}
