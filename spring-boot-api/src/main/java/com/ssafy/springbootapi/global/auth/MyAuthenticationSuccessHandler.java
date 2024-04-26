package com.ssafy.springbootapi.global.auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToeknRepository;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshToeknRepository refreshToeknRepository;
    private final UserRepository userRepository;

    public MyAuthenticationSuccessHandler(TokenProvider tokenProvider, RefreshToeknRepository refreshToeknRepository, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.refreshToeknRepository = refreshToeknRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 여기에서 UserService의 login을 실행
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        String accessToken = tokenProvider.generateToken(user, Duration.ofMinutes(1L));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofMinutes(30L));
        System.out.println("accessToken "+accessToken);
        refreshToeknRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .build());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getWriter(),new UserLoginResponse(accessToken,refreshToken));
    }
}
