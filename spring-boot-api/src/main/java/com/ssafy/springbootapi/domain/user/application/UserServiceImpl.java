package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO){
        User user = userRepository.save(requestDTO.toEntity());
        return UserSignUpResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Override
    public UserInfoResponseDTO getUserInfo(UserInfoRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(()->new RuntimeException(requestDTO.getEmail()+" 사용자 없음"));
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .addresses(user.getAddresses())
                .build();
    }

    @Override
    @Transactional
    public int updateUserInfo(UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(()->new RuntimeException(requestDTO.getEmail()+" 사용자 없음"));

        user.update(requestDTO.getEmail(), requestDTO.getPassword(), requestDTO.getName());

        return 1;
    }

}
