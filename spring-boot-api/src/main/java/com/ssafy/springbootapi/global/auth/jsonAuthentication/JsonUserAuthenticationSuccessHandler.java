package com.ssafy.springbootapi.global.auth.jsonAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * JSON 타입의 유저 로그인이 성공했을 때의 핸들러
 */
@Component
public class JsonUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public JsonUserAuthenticationSuccessHandler(UserRepository userRepository, TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * json 아이디 패스워드 인증 성공시 실행되는 메소드
     * RefreshToken : Http-Only cookie - 60분
     * AccessToken  : response body (UserLoginResponse DTO) - 15분
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authentication Authentication, 인증 정보
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        UUID id = user.getId();
        String accessToken = tokenProvider.generateToken(id, Duration.ofMinutes(15L));
        String refreshToken = tokenProvider.generateToken(id, Duration.ofMinutes(60L));

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
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
