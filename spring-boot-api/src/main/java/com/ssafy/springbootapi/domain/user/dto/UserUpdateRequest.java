package com.ssafy.springbootapi.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserUpdateRequest {
    public String password;
    public String name;
}
