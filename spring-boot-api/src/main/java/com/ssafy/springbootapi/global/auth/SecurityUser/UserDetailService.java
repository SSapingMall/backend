package com.ssafy.springbootapi.global.auth.SecurityUser;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(()->new UsernameNotFoundException(uuid+" 사용자 없음"));
        return new SecurityUser(user);
    }

    public UUID loadUserIdByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(email+" 사용자 없음"));
        return user.getId();
    }
}
