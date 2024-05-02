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

@RequestMapping("/api/v1/auth")
@ControllerAdvice
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 할 때 사용하는 API")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/token")
    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    public ResponseEntity<TokenResponse> token(HttpServletRequest request) {
        String refreshToken = null;
        // 요청에서 쿠키 배열을 가져옵니다.
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 쿠키 배열을 순회하면서 refreshToken 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        // refreshToken이 있는 경우 새로운 액세스 토큰을 발급받습니다.
        if (refreshToken != null) {
            String accessToken = authService.provideNewAccessToken(refreshToken);
            return ResponseEntity.ok(TokenResponse.builder().accessToken(accessToken).build());
        } else {
            // refreshToken이 없는 경우 적절한 예외 처리를 합니다.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
