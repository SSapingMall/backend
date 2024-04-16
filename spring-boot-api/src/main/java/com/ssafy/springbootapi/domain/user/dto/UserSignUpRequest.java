package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSignUpRequest {
    @NotBlank(message = "email is blank!")
    public String email;

    @NotBlank(message = "password is blank!")
    public String password;

    @NotBlank(message = "name is blank!")
    public String name;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .addresses(null)
                .build();
    }

    @Override
    public String toString() {
        return "UserSignUpRequestDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
