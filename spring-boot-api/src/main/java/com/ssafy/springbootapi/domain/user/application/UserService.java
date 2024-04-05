package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dto.UserInfoRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserInfoResponseDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpResponseDTO;

public interface UserService {
    UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO);
    UserInfoResponseDTO getUserInfo(UserInfoRequestDTO requestDTO);
}
