package com.donut.donut.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long userId;
    private String name;
    private String profileUrl;
    private Boolean isNotificationComment;
    private Boolean isNotificationFriend;
}
