package com.ssafy.springbootapi.global.auth.jsonAuthentication;

import com.ssafy.springbootapi.global.auth.SecurityUser.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonUserAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("username in auth provider:"+username);

        UserDetails u = userDetailService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, u.getPassword())) {
            // 암호일치하면 필요한 세부정보를 넣은 Authentication 객체를 반환
            return new UsernamePasswordAuthenticationToken(
                    username, null, u.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
