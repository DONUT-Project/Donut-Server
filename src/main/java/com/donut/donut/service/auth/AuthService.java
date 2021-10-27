package com.donut.donut.service.auth;

import com.donut.donut.payload.request.SignInRequest;
import com.donut.donut.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequest signInRequest);
    TokenResponse refreshToken(String refreshToken);
}
