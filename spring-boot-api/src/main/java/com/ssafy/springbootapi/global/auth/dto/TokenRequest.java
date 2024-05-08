package com.ssafy.springbootapi.global.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * access Token 재발급을 위한 요청 DTO
 */
@Getter
@Setter
public class TokenRequest {
    private String refreshToken;
}
