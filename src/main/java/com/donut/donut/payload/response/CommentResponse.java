package com.donut.donut.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private Long doneId;
    private Long userId;
    private String nickName;
    private String comment;
    private Boolean isPublic;
    private LocalDateTime writeAt;
    private List<ReCommentResponse> reComment;
}
