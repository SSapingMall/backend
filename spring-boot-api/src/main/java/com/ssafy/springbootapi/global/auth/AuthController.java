package com.ssafy.springbootapi.global.auth;

import com.ssafy.springbootapi.domain.user.dto.UserLoginRequest;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.aop.annotation.ToException;
import com.ssafy.springbootapi.global.auth.dto.TokenRequest;
import com.ssafy.springbootapi.global.auth.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 인증을 관리하는 컨트롤러 클래스.
 * 로그인 및 토큰 재발급 기능을 제공합니다.
 */
@RequestMapping("/api/v1/auth")
@ControllerAdvice
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    /**
     * 사용자 로그인을 처리하고 로그인 응답을 반환합니다.
     *
     * @param userLoginRequest 로그인 요청 데이터
     * @return 로그인 성공 시 상태 코드 200과 함께 로그인 응답 데이터를 포함한 ResponseEntity 객체 반환
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 할 때 사용하는 API")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 저장된 리프레시 토큰을 검증하고 새 액세스 토큰을 발급합니다.
     *
     * @param request HTTP 요청 정보를 포함하는 HttpServletRequest 객체
     * @return 새로운 액세스 토큰을 포함한 ResponseEntity 객체. 리프레시 토큰이 유효하지 않거나 없을 경우 401 Unauthorized 반환
     */
    @PostMapping("/token")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    public ResponseEntity<TokenResponse> token(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken != null) {
            String accessToken = authService.provideNewAccessToken(refreshToken);
            return ResponseEntity.ok(TokenResponse.builder().accessToken(accessToken).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
