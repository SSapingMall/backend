package com.ssafy.springbootapi.global.auth;

import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import com.ssafy.springbootapi.global.error.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public String provideNewAccessToken(String refreshToken){
        // refresh 토큰이 유효하다면 새 access 토큰 발급
        // refresh 토큰이 유효하지 않다면 exception

        String accessToken = "";
        if (tokenProvider.validToken(refreshToken)){
            refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->
                    new InvalidTokenException("invalid refresh token!")
            );
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            accessToken = tokenProvider.generateToken(UUID.fromString(authentication.getName()), Duration.ofMinutes(1L));
        }else {
            throw new InvalidTokenException("invalid refresh token!");
        }
        return accessToken;
    }

}