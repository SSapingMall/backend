package com.ssafy.springbootapi.domain.user.application;

import com.ssafy.springbootapi.domain.user.dto.*;

public interface UserService {
    UserSignUpResponseDTO signUp(UserSignUpRequestDTO requestDTO);
    UserInfoResponseDTO getUserInfo(UserInfoRequestDTO requestDTO);
    int updateUserInfo(UserUpdateRequestDTO requestDTO);
}
