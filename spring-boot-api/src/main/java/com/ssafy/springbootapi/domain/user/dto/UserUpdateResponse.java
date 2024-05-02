package com.ssafy.springbootapi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class UserUpdateResponse {
    private String email;
    private String name;
    private LocalDateTime createdAt;
}
