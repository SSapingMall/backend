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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO){
        userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(()->new UserDuplicatedException(requestDTO.getEmail()+"이미 존재하는 사용자"));

        User user = userRepository.save(requestDTO.toEntity());
        return UserSignUpResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


    @Override
    public UserInfoResponseDTO getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .addresses(user.getAddresses())
                .build();
    }

    @Override
    @Transactional
    public UserUpdateResponseDTO updateUserInfo(UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getId())
                .orElseThrow(()->new UserNotFoundException(requestDTO.getEmail()+" 사용자 없음"));

        user.update(requestDTO.getEmail(), requestDTO.getPassword(), requestDTO.getName());

        return UserUpdateResponseDTO.builder().email(user.getEmail()).name(user.getName()).build();
    }

    @Override
    public boolean removeUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id+" 사용자 없음"));
        userRepository.delete(user);
        return true;
    }


}
