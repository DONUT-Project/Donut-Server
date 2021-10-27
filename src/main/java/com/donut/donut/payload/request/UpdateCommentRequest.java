package com.donut.donut.payload.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private String comment;
    private Boolean isPublic;
}
