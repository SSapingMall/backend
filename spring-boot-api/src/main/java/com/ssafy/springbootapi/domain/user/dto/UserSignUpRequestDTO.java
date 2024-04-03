package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSignUpRequestDTO {
    private String email;
    private String password;
    private String name;

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
