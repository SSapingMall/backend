package com.ssafy.springbootapi.global.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * access Token 재발급을 위한 응답 DTO
 */
@Getter
@Setter
@Builder
public class TokenResponse {
    private String accessToken;
}
