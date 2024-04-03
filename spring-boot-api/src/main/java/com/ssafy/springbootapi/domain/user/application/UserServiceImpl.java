package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO){
        System.out.println(requestDTO);
        User user = userRepository.save(requestDTO.toEntity());
        System.out.println(user);
        return UserSignUpResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}