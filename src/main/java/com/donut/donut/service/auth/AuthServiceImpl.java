package com.donut.donut.service.auth;

import com.donut.donut.entity.device_token.DeviceToken;
import com.donut.donut.entity.device_token.repository.DeviceTokenRepository;
import com.donut.donut.entity.refresh_token.RefreshToken;
import com.donut.donut.entity.refresh_token.repository.RefreshTokenRepository;
import com.donut.donut.entity.user.User;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.InvalidTokenException;
import com.donut.donut.error.exceptions.LoginFailedException;
import com.donut.donut.error.exceptions.RefreshTokenException;
import com.donut.donut.error.exceptions.UserNotFoundException;
import com.donut.donut.payload.request.SignInRequest;
import com.donut.donut.payload.response.TokenResponse;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    private final JwtProvider jwtProvider;

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        return userRepository.findByKakaoId(signInRequest.getKakaoId())
                .map(user -> {
                    if(!user.getUserImageUrl().equals(signInRequest.getProfileUrl()))
                        user.updateImageUrl(signInRequest.getProfileUrl());

                    if(!user.getNickName().equals(signInRequest.getNickName()))
                        user.updateNickName(signInRequest.getNickName());

                    userRepository.save(user);

                    String accessToken = jwtProvider.generateAccessToken(signInRequest.getKakaoId());
                    String refreshToken = jwtProvider.generateRefreshToken(signInRequest.getKakaoId());

                    refreshTokenRepository.save(
                            RefreshToken.builder()
                                    .kakaoId(user.getKakaoId())
                                    .refreshToken(refreshToken)
                                    .build()
                    );

                    if(!deviceTokenRepository.existsByDeviceToken(signInRequest.getDeviceToken())) {
                        deviceTokenRepository.save(
                                DeviceToken.builder()
                                        .deviceToken(signInRequest.getDeviceToken())
                                        .user(user)
                                        .build()
                        );
                    }

                    return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(LoginFailedException::new);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtProvider.isRefreshToken(refreshToken) || !jwtProvider.validateToken(refreshToken))
            throw new InvalidTokenException();

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .map(refreshToken1 -> {
                    String newAccessToken = jwtProvider.generateAccessToken(refreshToken1.getKakaoId());
                    String newRefreshToken = jwtProvider.generateRefreshToken(refreshToken1.getKakaoId());

                    refreshTokenRepository.save(refreshToken1.updateRefreshToken(newRefreshToken));

                    return TokenResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .build();
                })
                .orElseThrow(RefreshTokenException::new);
    }

    @Override
    @Transactional
    public void logout(String token, String deviceToken) {
        User user = userRepository.findByKakaoId(jwtProvider.getKakaoId(token))
                .orElseThrow(UserNotFoundException::new);

        if(deviceTokenRepository.existsByDeviceTokenAndUser(deviceToken, user))
            deviceTokenRepository.deleteByDeviceTokenAndUser(deviceToken, user);
    }
}
