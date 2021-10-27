package com.donut.donut.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReCommentResponse {
    private Long reCommentId;
    private Long commentId;
    private Long userId;
    private String nickName;
    private Boolean isPublic;
    private String comment;
    private LocalDateTime writeAt;
}
