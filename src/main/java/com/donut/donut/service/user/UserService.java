package com.donut.donut.service.user;

import com.donut.donut.payload.request.SignUpRequest;
import com.donut.donut.payload.response.UserResponse;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
    UserResponse getMyInfo(String token);
}
