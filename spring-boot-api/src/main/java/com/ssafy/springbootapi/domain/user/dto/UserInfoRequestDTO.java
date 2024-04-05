package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoRequestDTO {
    @NotBlank(message = "email is blank!")
    private String email;
}
