package com.donut.donut.service.user;

import com.donut.donut.payload.request.SignUpRequest;
import com.donut.donut.payload.response.UserResponse;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
    UserResponse getMyInfo(String token);
    UserResponse getFriends(String token, Long kakaoId);
    void updateIsNotificationComment(String token, Boolean isNotificationComment);
    void updateIsNotificationFriend(String token, Boolean isNotificationFriend);
    void deleteUser(String token);
}
