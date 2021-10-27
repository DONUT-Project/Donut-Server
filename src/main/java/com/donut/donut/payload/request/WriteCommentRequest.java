package com.donut.donut.payload.request;

import lombok.Getter;

@Getter
public class WriteCommentRequest {
    private String comment;
    private Boolean isPublic;
}
