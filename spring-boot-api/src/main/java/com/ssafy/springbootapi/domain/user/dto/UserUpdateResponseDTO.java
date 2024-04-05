package com.ssafy.springbootapi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserUpdateResponseDTO {
    private String email;
    private String name;
}
