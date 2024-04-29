package com.ssafy.springbootapi.global.auth.jsonAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class JsonUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public JsonUserAuthenticationSuccessHandler(TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 여기에서 UserService의 login을 실행
        String email = authentication.getName();

        String accessToken = tokenProvider.generateToken(email, Duration.ofMinutes(15L));
        String refreshToken = tokenProvider.generateToken(email, Duration.ofMinutes(60L));
        refreshTokenRepository.save(RefreshToken.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build());

        // Refresh Token HttpOnly Cookie에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true); // HTTPS를 사용하는 경우에만 보낼 수 있도록 설정
        refreshTokenCookie.setPath("/api/v1/auth/token"); // 토큰 재발급시에만 쿠키를 사용할 수 있다.
        refreshTokenCookie.setMaxAge(60 * 60); // 유효 기간 설정

        response.addCookie(refreshTokenCookie);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(),new UserLoginResponse(accessToken));
    }
}
