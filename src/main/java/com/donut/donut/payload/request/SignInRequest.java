package com.donut.donut.payload.request;

import lombok.Getter;

@Getter
public class SignInRequest {
    private Long kakaoId;
    private String nickName;
    private String profileUrl;
}
