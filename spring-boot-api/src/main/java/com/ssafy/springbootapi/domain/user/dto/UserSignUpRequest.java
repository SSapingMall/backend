package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Getter
public class UserSignUpRequest {

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
