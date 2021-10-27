package com.donut.donut.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST("bad request", 400),
    LOGIN_FAILED("login failed", 403),
    REFRESH_TOKEN_FAILED("refresh token failed", 403),
    INVALID_TOKEN("invalid token", 403),
    COMMENT_NOT_FOUND("comment not found", 404),
    RECOMMENT_NOT_FOUND("re comment not found", 404),
    ALREADY_USER_SIGNED("already user signed", 403),
    ALREADY_FRIEND("already friend", 403),
    FRIEND_NOT_FOUND("friend not found", 404),
    NOT_MY_COMMENT("not my comment", 403),
    DONE_NOT_FOUND("done not found", 404),
    USER_NOT_FOUND("user not found", 404);

    private String message;
    private int code;
}
