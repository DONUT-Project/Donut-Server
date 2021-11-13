package com.donut.donut.controller;

import com.donut.donut.payload.request.SignInRequest;
import com.donut.donut.payload.response.TokenResponse;
import com.donut.donut.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @DeleteMapping("/{deviceToken}")
    public void logout(@PathVariable String deviceToken,
                       @RequestHeader("Authorization") String token) {
        authService.logout(token, deviceToken);
    }
}
