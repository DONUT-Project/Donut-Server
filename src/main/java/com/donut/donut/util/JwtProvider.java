package com.donut.donut.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.access}")
    private long accessToken;

    @Value("${jwt.refresh}")
    private long refreshToken;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateAccessToken(Long kakaoId) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessToken * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("type", "access_token")
                .setSubject(kakaoId.toString())
                .compact();
    }

    public String generateRefreshToken(Long kakaoId) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshToken * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("type", "refresh_token")
                .setSubject(kakaoId.toString())
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody().getSubject();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("type").equals("refresh_token");
    }

    public Long getKakaoId(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject());
    }
}
