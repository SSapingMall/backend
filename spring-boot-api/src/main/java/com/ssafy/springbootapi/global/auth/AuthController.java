package com.ssafy.springbootapi.global.auth;

import com.ssafy.springbootapi.domain.user.dto.UserLoginRequest;
import com.ssafy.springbootapi.domain.user.dto.UserLoginResponse;
import com.ssafy.springbootapi.global.aop.annotation.ToException;
import com.ssafy.springbootapi.global.auth.dto.TokenRequest;
import com.ssafy.springbootapi.global.auth.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<TokenResponse> token(@RequestBody TokenRequest refreshToken) {
        String accessToken = authService.provideNewAccessToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok(TokenResponse.builder().accessToken(accessToken).build());
    }

}
