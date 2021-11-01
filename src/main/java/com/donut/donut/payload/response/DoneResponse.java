package com.donut.donut.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class DoneResponse {
    private Long doneId;
    private String userName;
    private Long kakaoId;
    private LocalDate writeAt;
    private String title;
    private String content;
    private Boolean isPublic;
}
