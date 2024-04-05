package com.ssafy.springbootapi.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @NotBlank(message = "email is blank!")
    public String email;

    @NotBlank(message = "password is blank!")
    public String password;

    @NotBlank(message = "name is blank!")
    public String name;
}
