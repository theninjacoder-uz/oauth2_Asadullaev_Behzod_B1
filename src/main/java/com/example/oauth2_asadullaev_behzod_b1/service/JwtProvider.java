package com.example.oauth2_asadullaev_behzod_b1.service;

import com.example.oauth2_asadullaev_behzod_b1.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.access.token.secret.key}")
    public String accessTokenSecretKey;

    @Value("${jwt.refresh.token.secret.key}")
    public String refreshTokenSecretKey;

    @Value("${jwt.access.token.expired.time}")
    public long accessTokenExpiredTime;

    @Value("${jwt.refresh.token.expired.time}")
    public long refreshTokenExpiredTime;

    public String generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, accessTokenSecretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenExpiredTime))
                .setSubject(userEntity.getUsername())
                .claim("authorities", userEntity.getPermission())
                .compact();
    }

    public String generateRefreshToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, refreshTokenSecretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + refreshTokenExpiredTime))
                .setSubject(userEntity.getUsername())
                .claim("authorities", userEntity.getPermission())
                .compact();
    }

    public Claims parseAccessToken(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(accessTokenSecretKey)
                    .parseClaimsJws(accessToken).getBody();
        } catch (Exception ex) {
            throw new RuntimeException("REFRESH TOKEN EXPIRED");
        }
    }

    public String getAccessTokenFromRefreshToken(String refreshToken)  {
        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(refreshTokenSecretKey)
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("REFRESH TOKEN EXPIRED");
        }

        return generateAccessToken(body.get("user", UserEntity.class));
    }


}
