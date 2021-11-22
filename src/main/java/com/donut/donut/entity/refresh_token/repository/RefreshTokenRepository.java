package com.donut.donut.entity.refresh_token.repository;

import com.donut.donut.entity.refresh_token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteAllByKakaoId(Long kakaoId);
}
