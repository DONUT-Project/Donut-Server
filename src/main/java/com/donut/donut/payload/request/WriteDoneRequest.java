package com.donut.donut.payload.request;

import lombok.Getter;

@Getter
public class WriteDoneRequest {
    private String title;
    private String content;
    private Boolean isPublic;
}
