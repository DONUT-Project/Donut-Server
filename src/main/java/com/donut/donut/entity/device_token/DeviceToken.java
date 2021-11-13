package com.donut.donut.entity.device_token;

import com.donut.donut.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deviceId;

    @ManyToOne
    @JoinColumn(name = "kakaoId")
    private User user;

    private String deviceToken;
}
