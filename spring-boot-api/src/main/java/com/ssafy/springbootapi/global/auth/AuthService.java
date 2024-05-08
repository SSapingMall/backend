package com.ssafy.springbootapi.global.auth;

import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import com.ssafy.springbootapi.global.error.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * 인증 관련 서비스를 제공하는 클래스.
 * 주로 토큰 생성 및 검증을 담당합니다.
 */
@RequiredArgsConstructor
@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    /**
     * 제공된 리프레시 토큰의 유효성을 검사하고, 유효한 경우 새로운 액세스 토큰을 발급합니다.
     * 리프레시 토큰이 유효하지 않을 경우 InvalidTokenException 예외를 발생시킵니다.
     *
     * @param refreshToken 검증하고자 하는 리프레시 토큰
     * @return 새로 발급된 액세스 토큰 문자열
     * @throws InvalidTokenException 리프레시 토큰이 유효하지 않을 경우 발생
     */
    public String provideNewAccessToken(String refreshToken) {
        String accessToken = "";
        if (tokenProvider.validToken(refreshToken)){
            refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() ->
                    new InvalidTokenException("invalid refresh token!")
            );
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            accessToken = tokenProvider.generateToken(UUID.fromString(authentication.getName()), Duration.ofMinutes(15L));
        } else {
            throw new InvalidTokenException("invalid refresh token!");
        }
        return accessToken;
    }
}
