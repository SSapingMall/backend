package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.domain.UserMapper;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*
 * TODO:: 사용자 정의 exception
 *  - user not found exception
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
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


    public UserInfoResponse getUserInfo(UUID id) {
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
    public UserUpdateResponse updateUserInfo(UUID id, UserUpdateRequest requestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));

        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userMapper.updateUserFromDto(requestDTO,user);

        user = userRepository.save(user);

        return UserUpdateResponse.builder().email(user.getEmail()).name(user.getName()).createdAt(user.getCreatedAt()).build();
    }

    @Transactional
    public void removeUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));

        // refresh 토큰 삭제
        refreshTokenRepository.findByUserId(id)
                        .ifPresent(refreshTokenRepository::delete);

        userRepository.delete(user);
    }


}
