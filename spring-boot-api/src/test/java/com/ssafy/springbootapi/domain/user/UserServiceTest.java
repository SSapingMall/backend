package com.ssafy.springbootapi.domain.user;

import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.application.UserServiceImpl;
import com.ssafy.springbootapi.domain.user.dao.UserRepository;
import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpRequestDTO;
import com.ssafy.springbootapi.domain.user.dto.UserSignUpResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @DisplayName("회원가입 서비스 로직 테스트")
    @Tag("unit-test")
    @Test
    public void userSignUpHappyFlowTest(){
        // given
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
}