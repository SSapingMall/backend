package com.ssafy.springbootapi.domain.user;

import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.domain.UserMapper;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.domain.user.exception.UserDuplicatedException;
import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Spy
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    UserService userService;

    @DisplayName("유저 회원가입 성공 테스트")
    @Tag("unit-test")
    @Tag("happy-flow")
    @Test
    public void 유저회원가입성공테스트(){
        // given.
        UserSignUpRequest userSignUpRequest
                = new UserSignUpRequest("kkho9654@naver.com","1234","kkh");
        User userToSave = userSignUpRequest.toEntity();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        given(userRepository.save(any(User.class)))
                .willReturn(userToSave);
        // when
        UserSignUpResponse userSignUpResponse = userService.signUp(userSignUpRequest);

        // then
        Assertions.assertThat(userSignUpResponse.getEmail())
                .isEqualTo("kkho9654@naver.com");
    }

    @DisplayName("유저 회원가입 중복사용자 예외테스트")
    @Tag("unit-test")
    @Tag("exception-flow")
    @Test
    public void 유저회원가입중복사용자예외테스트(){
        // given
        UserSignUpRequest requestDTO
                = new UserSignUpRequest("kkho9654@naver.com","123","kkh");
        given(userRepository.findByEmail("kkho9654@naver.com"))
                .willThrow(UserDuplicatedException.class);

        // when then
        Assertions.assertThatThrownBy(()->userService.signUp(requestDTO))
                .isInstanceOf(UserDuplicatedException.class);

        verify(userRepository, never())
                .save(any());

    }

    @DisplayName("유저 정보읽기 성공테스트")
    @Tag("happy-flow")
    @Tag("unit-test")
    @Test
    public void 유저정보읽기성공테스트(){
        // given
        Long id = 1L;
        given(userRepository.findById(id))
                .willReturn(Optional.of(User.builder()
                        .email("kkho9654@naver.com")
                        .name("kkh")
                        .build())
                );

        // when
        UserInfoResponse userInfoResponse = userService.getUserInfo(id);

        // then
        Assertions.assertThat(userInfoResponse.getEmail())
                .isEqualTo("kkho9654@naver.com");
        Assertions.assertThat(userInfoResponse.getName())
                .isEqualTo("kkh");
    }

    @DisplayName("유저 정보 읽기 존재하지 않는 사용자 예외테스트")
    @Test
    void 유저정보읽기존재하지않는사용자예외테스트(){
        // given
        Long id = 1L;
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(()->userService.getUserInfo(id))
                        .isInstanceOf(UserNotFoundException.class);

    }
    @DisplayName("유저 수정 성공 테스트")
    @Tag("unit-test")
    @Tag("happy-flow")
    @Test
    void 유저수정성공테스트() {
        // given
        UserUpdateRequest requestDTO
                = new UserUpdateRequest("kkho9654@naver2.com","1112","3333");
        User user = User.builder()
                .id(1L)
                .email("kkho9654@naver.com")
                .password("1234")
                .name("kkh").build();
        User updatedUser = userMapper.toEntity(requestDTO);
        given(userRepository.findById(1L))
                .willReturn(Optional.of(user));

        doNothing().when(userMapper).updateUserFromDto(requestDTO, user);

        given(userRepository.save(user))
                .willReturn(updatedUser);

        // when
        UserUpdateResponse responseDTO = userService.updateUserInfo(1L, requestDTO);

        // then
        Assertions.assertThat(responseDTO.getEmail())
                .isEqualTo(requestDTO.getEmail());
        Assertions.assertThat(responseDTO.getName())
                .isEqualTo(requestDTO.getName());
    }
    @DisplayName("유저 업데이트 존재하지 않는 사용자 예외 테스트")
    @Test
    void 유저업데이트존재하지않는사용자예외테스트(){
        // given
        UserMapper userMapper = mock(UserMapper.class);
        Long id = 1L;
        UserUpdateRequest requestDTO = new UserUpdateRequest("test","test","test");
        given(userRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(()->userService.updateUserInfo(id, requestDTO))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(userMapper,never()).updateUserFromDto(any(),any());
    }
    @DisplayName("유저 삭제 성공 테스트")
    @Tag("happy-flow")
    @Test
    void 유저삭제성공테스트() {
        // given
        Long id = 1L;
        User user = User.builder()
                        .id(1L)
                        .email("kkho9654@naver.com")
                        .name("kkh").password("123").build();
        given(userRepository.findById(id))
                .willReturn(Optional.of(user));
        // when
        userService.removeUser(id);

        // then
        verify(userRepository).delete(user);
    }

    @DisplayName("유저 삭제 존재하지않는 사용자 예외 테스트")
    @Test
    void 유저삭제존재하지않는사용자예외테스트(){
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
