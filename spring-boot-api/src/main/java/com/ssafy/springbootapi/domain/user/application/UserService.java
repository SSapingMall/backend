package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.domain.UserMapper;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import com.ssafy.springbootapi.global.auth.SecurityUser;
import com.ssafy.springbootapi.global.auth.jwt.TokenProvider;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToeknRepository;
import com.ssafy.springbootapi.global.auth.jwt.refreshToken.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final TokenProvider tokenProvider;
    private final RefreshToeknRepository refreshToeknRepository;

    public UserLoginResponse login(){
        System.out.println("In UserService Login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            String email = (String) authentication.getPrincipal();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new UserNotFoundException(email+" 사용자 없음"));

            String accessToken = tokenProvider.generateToken(user, Duration.ofMinutes(1L));
            String refreshToken = tokenProvider.generateToken(user, Duration.ofMinutes(30L));
            System.out.println("accessToken "+accessToken);
            refreshToeknRepository.save(RefreshToken.builder()
                    .userId(user.getId())
                    .refreshToken(refreshToken)
                    .build());

            return new UserLoginResponse(accessToken, refreshToken);
        }
        return null;
    }

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
                .addresses(user.getAddresses())
                .build();
    }

    @Transactional
    public UserUpdateResponse updateUserInfo(UserUpdateRequest requestDTO) {
        User user = userRepository.findById(requestDTO.getId())
                .orElseThrow(()->new UserNotFoundException(requestDTO.getEmail()+" 사용자 없음"));

        userMapper.updateUserFromDto(requestDTO,user);
        user = userRepository.save(user);

        return UserUpdateResponse.builder().email(user.getEmail()).name(user.getName()).build();
    }

    public void removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        userRepository.delete(user);
    }


}
