package com.donut.donut.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long kakaoId;

    private String nickName;

    private String userImageUrl;

    public void updateImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;

    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }
}
