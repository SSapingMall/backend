package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * TODO:: 사용자 정의 exception
 *  - user not found exception
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserSignUpResponse signUp(UserSignUpRequest requestDTO){
        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new UserDuplicatedException(requestDTO.getEmail()+"이미 존재하는 사용자");
        }
        User user = userRepository.save(requestDTO.toEntity());
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

        user.update(requestDTO.getEmail(), requestDTO.getPassword(), requestDTO.getName());

        return UserUpdateResponse.builder().email(user.getEmail()).name(user.getName()).build();
    }

    public void removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        userRepository.delete(user);
    }


}
