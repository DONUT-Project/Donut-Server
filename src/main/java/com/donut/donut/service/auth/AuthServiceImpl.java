package com.donut.donut.service.auth;

import com.donut.donut.entity.refresh_token.RefreshToken;
import com.donut.donut.entity.refresh_token.repository.RefreshTokenRepository;
import com.donut.donut.entity.user.repository.UserRepository;
import com.donut.donut.error.exceptions.InvalidTokenException;
import com.donut.donut.error.exceptions.LoginFailedException;
import com.donut.donut.error.exceptions.RefreshTokenException;
import com.donut.donut.payload.request.SignInRequest;
import com.donut.donut.payload.response.TokenResponse;
import com.donut.donut.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider jwtProvider;

    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        return userRepository.findByKakaoId(signInRequest.getKakaoId())
                .map(user -> {
                    if(!user.getUserImageUrl().equals(signInRequest.getProfileUrl()))
                        user.updateImageUrl(signInRequest.getProfileUrl());

                    if(!user.getNickName().equals(signInRequest.getNickName()))
                        user.updateNickName(signInRequest.getNickName());

                    String accessToken = jwtProvider.generateAccessToken(signInRequest.getKakaoId());
                    String refreshToken = jwtProvider.generateRefreshToken(signInRequest.getKakaoId());

                    refreshTokenRepository.save(
                            RefreshToken.builder()
                                    .kakaoId(user.getKakaoId())
                                    .refreshToken(refreshToken)
                                    .build()
                    );

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
}
