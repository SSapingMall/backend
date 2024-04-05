package com.ssafy.springbootapi.domain.user;

import com.ssafy.springbootapi.domain.user.application.UserServiceImpl;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @DisplayName("회원가입 happy flow")
    @Tag("unit-test")
    @Test
    public void userSignUpHappyFlowTest(){
        // given.
        UserSignUpRequestDTO userSignUpRequestDTO
                = new UserSignUpRequestDTO("kkho9654@naver.com","1234","kkh");
        User userToSave = userSignUpRequestDTO.toEntity();
        given(userRepository.save(any(User.class)))
                .willReturn(userToSave);
        // when
        UserSignUpResponseDTO userSignUpResponseDTO = userService.signUp(userSignUpRequestDTO);

        // then
        Assertions.assertThat(userSignUpResponseDTO.getEmail())
                .isEqualTo("kkho9654@naver.com");
    }

    @DisplayName("유저 정보 받기 happy flow")
    @Tag("happy-flow")
    @Tag("unit-test")
    @Test
    public void getUserInfoHappyFlowTest(){
        // given
        Long id = 1L;
        given(userRepository.findById(id))
                .willReturn(Optional.of(User.builder()
                        .email("kkho9654@naver.com")
                        .name("kkh")
                        .build())
                );

        // when
        UserInfoResponseDTO userInfoResponseDTO = userService.getUserInfo(id);

        // then
        Assertions.assertThat(userInfoResponseDTO.getEmail())
                .isEqualTo("kkho9654@naver.com");
        Assertions.assertThat(userInfoResponseDTO.getName())
                .isEqualTo("kkh");
    }

    @DisplayName("회원가입 happy flow")
    @Tag("unit-test")
    @Tag("happy-flow")
    @Test
    public void userUpdateHappyFlowTest(){
        // given.
        UserSignUpRequestDTO userSignUpRequestDTO
                = new UserSignUpRequestDTO("kkho9654@naver.com","1234","kkh");
        User userToSave = userSignUpRequestDTO.toEntity();
        given(userRepository.save(any(User.class)))
                .willReturn(userToSave);
        // when
        UserSignUpResponseDTO userSignUpResponseDTO = userService.signUp(userSignUpRequestDTO);

        // then
        Assertions.assertThat(userSignUpResponseDTO.getEmail())
                .isEqualTo("kkho9654@naver.com");
    }

    @DisplayName("회원수정 happy flow")
    @Tag("unit-test")
    @Tag("happy-flow")
    @Test
    void updateUserInfoHappyFlow() {
        // given
        UserUpdateRequestDTO requestDTO
                = new UserUpdateRequestDTO(1L,"kkho9654@naver2.com","1112","3333");

        // "kkho9654@naver.com","1234","kkh"
        given(userRepository.findById(1L))
                .willReturn(Optional.of(User.builder()
                        .id(1L)
                        .email("kkho9654@naver.com")
                        .password("1234")
                        .name("kkh").build()));

        // when
        UserUpdateResponseDTO responseDTO = userService.updateUserInfo(requestDTO);

        // then
        Assertions.assertThat(responseDTO.getEmail())
                .isEqualTo("kkho9654@naver2.com");
        Assertions.assertThat(responseDTO.getName())
                .isEqualTo("3333");
    }


}
