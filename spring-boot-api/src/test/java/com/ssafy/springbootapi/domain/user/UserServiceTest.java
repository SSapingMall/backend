package com.ssafy.springbootapi.domain.user;

import com.ssafy.springbootapi.domain.user.application.UserServiceImpl;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @DisplayName("CREATE - 회원가입 happy flow")
    @Tag("unit-test")
    @Tag("happy-flow")
    @Test
    public void userSignUpHappyFlowTest(){
        // given.
        UserSignUpRequestDTO userSignUpRequestDTO
                = new UserSignUpRequestDTO("kkho9654@naver.com","1234","kkh");
        User userToSave = userSignUpRequestDTO.toEntity();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        given(userRepository.save(any(User.class)))
                .willReturn(userToSave);
        // when
        UserSignUpResponseDTO userSignUpResponseDTO = userService.signUp(userSignUpRequestDTO);

        // then
        Assertions.assertThat(userSignUpResponseDTO.getEmail())
                .isEqualTo("kkho9654@naver.com");
    }

    @DisplayName("CREATE - 회원가입 실패 (이미 존재하는 사용자)")
    @Tag("unit-test")
    @Tag("exception-flow")
    @Test
    public void signUpDuplicatedUserExceptionTest(){
        // given
        UserSignUpRequestDTO requestDTO
                = new UserSignUpRequestDTO("kkho9654@naver.com","123","kkh");
        given(userRepository.findByEmail("kkho9654@naver.com"))
                .willThrow(UserDuplicatedException.class);

        // when then
        Assertions.assertThatThrownBy(()->userService.signUp(requestDTO))
                .isInstanceOf(UserDuplicatedException.class);

        verify(userRepository, never())
                .save(any());

    }

    @DisplayName("READ - 회원 정보 happy flow")
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

    @DisplayName("READ - 회원 정보 읽기 실패")
    @Test
    void getUserInfoUserNotFoundExceptionTest(){
        // given
        Long id = 1L;
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(()->userService.getUserInfo(id))
                        .isInstanceOf(UserNotFoundException.class);

    }
    @DisplayName("UPDATE - 회원수정 happy flow")
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
                .isEqualTo(requestDTO.getEmail());
        Assertions.assertThat(responseDTO.getName())
                .isEqualTo(requestDTO.getName());
    }
    @DisplayName("UPDATE - 회원 정보 읽기 실패")
    @Test
    void updateUserInfoUserNotFoundExceptionTest(){
        // given
        User user = mock(User.class);
        Long id = 1L;
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(1L,"test","test","test");
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(()->userService.updateUserInfo(requestDTO))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(user,never()).update(anyString(),anyString(),anyString());
    }
    @DisplayName("DELETE - 회원 삭제 성공")
    @Tag("happy-flow")
    @Test
    void userRemoveHappyFlowTest() {
        // given
        Long id = 1L;
        User user = User.builder()
                        .id(1L)
                        .email("kkho9654@naver.com")
                        .name("kkh").password("123").build();
        given(userRepository.findById(id))
                .willReturn(Optional.of(user));
        // when
        boolean result = userService.removeUser(id);

        // then
        Assertions.assertThat(result).isEqualTo(true);
        verify(userRepository).delete(user);
    }

    @DisplayName("DELETE - 회원 삭제 실패 - 존재하지 않는 사용자")
    @Test
    void userRemoveExceptionTest(){
        // given
        Long id = 1L;
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(()->userService.removeUser(id))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(userRepository, never()).delete(any());
    }
}
