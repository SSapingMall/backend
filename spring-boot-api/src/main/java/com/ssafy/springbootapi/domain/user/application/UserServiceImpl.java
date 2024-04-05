package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.UserInfoRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserInfoResponseDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO){
        User user = userRepository.save(requestDTO.toEntity());
        return UserSignUpResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Override
    public UserInfoResponseDTO getUserInfo(UserInfoRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail());
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .addresses(user.getAddresses())
                .build();
    }

}
