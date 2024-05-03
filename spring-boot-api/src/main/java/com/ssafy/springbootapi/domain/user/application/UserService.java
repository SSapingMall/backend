package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.domain.UserMapper;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

/*
 * TODO:: 사용자 정의 exception
 *  - user not found exception
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserSignUpResponse signUp(UserSignUpRequest requestDTO){
        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new UserDuplicatedException(requestDTO.getEmail()+"이미 존재하는 사용자");
        }
        User user = userRepository.save(
                User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword())) // password 암호화
                .name(requestDTO.getName())
                .addresses(null)
                .build());

        return UserSignUpResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


    public UserInfoResponse getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        return UserInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .addresses(user.getAddresses())
                .build();
    }

    @Transactional
    public UserUpdateResponse updateUserInfo(Long id, UserUpdateRequest requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(requestDTO.getEmail()+" 사용자 없음"));

        userMapper.updateUserFromDto(requestDTO,user);
        user = userRepository.save(user);

        return UserUpdateResponse.builder().email(user.getEmail()).name(user.getName()).createdAt(user.getCreatedAt()).build();
    }

    public void removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        userRepository.delete(user);
    }


}
