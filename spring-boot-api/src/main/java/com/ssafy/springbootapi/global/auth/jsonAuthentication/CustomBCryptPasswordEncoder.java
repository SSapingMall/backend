package com.ssafy.springbootapi.global.auth.jsonAuthentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * BCrypt 방식으로 사용자의 비밀번호를 사용하기 위한 PasswordEncoder
 */
@Component
public class CustomBCryptPasswordEncoder implements PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomBCryptPasswordEncoder() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * @param rawPassword 비밀번호 원본
     * @return encodedPassword 암호화된 비밀번호
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    /**
     * 로그인시 사용자 입력암호와 db에 저장된 인코딩 암호와 비교
     * @param rawPassword 사용자 입력 암호
     * @param encodedPassword db에 저장된 인코딩 암호
     * @return boolean
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
