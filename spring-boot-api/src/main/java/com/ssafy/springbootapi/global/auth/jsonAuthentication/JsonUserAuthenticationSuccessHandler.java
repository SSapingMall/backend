package com.ssafy.springbootapi.global.auth.jsonAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        String email = authentication.getName();

        String accessToken = tokenProvider.generateToken(email, Duration.ofMinutes(1L));
        String refreshToken = tokenProvider.generateToken(email, Duration.ofMinutes(30L));
        System.out.println("accessToken "+accessToken);
        refreshTokenRepository.save(RefreshToken.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(),new UserLoginResponse(accessToken,refreshToken));
    }
}
