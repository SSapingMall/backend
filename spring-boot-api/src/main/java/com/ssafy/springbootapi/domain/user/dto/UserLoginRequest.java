package com.ssafy.springbootapi.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginRequest {

    @NotBlank(message = "email is blank!")
    public String email;

    @NotBlank(message = "password is blank!")
    public String password;

    @Override
    public String toString() {
        return "UserSignUpRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
