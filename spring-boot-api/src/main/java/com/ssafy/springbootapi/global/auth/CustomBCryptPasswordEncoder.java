package com.ssafy.springbootapi.global.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomBCryptPasswordEncoder implements PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomBCryptPasswordEncoder() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        // 여기에 비밀번호 인코딩 전에 추가 로직을 구현할 수 있습니다.
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 여기에 매칭 로직 전에 추가 검사를 구현할 수 있습니다.
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
