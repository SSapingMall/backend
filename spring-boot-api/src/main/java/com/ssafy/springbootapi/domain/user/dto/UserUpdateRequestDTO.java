package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserUpdateRequestDTO {
    @NotBlank(message = "id is blank!")
    public Long id;

    @NotBlank(message = "email is blank!")
    public String email;

    @NotBlank(message = "password is blank!")
    public String password;

    @NotBlank(message = "name is blank!")
    public String name;

    @Override
    public String toString() {
        return "UserSignUpRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
