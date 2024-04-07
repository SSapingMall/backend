package com.ssafy.springbootapi.domain.user.api;

import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.dto.*;
import com.ssafy.springbootapi.global.aop.annotation.ToException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ToException
    @Operation(summary = "회원가입", description = "회원가입 할 때 사용하는 API")
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest userSignUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(userSignUpRequest));
    }

    @GetMapping("/{id}")
    @ToException
    @Operation(summary = "회원 정보", description = "유저정보 얻을 때 사용하는 API")
    public ResponseEntity<UserInfoResponse> getInfo(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserInfo(id));
    }

    @PutMapping
    @ToException
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 할 때 사용하는 API")
    public ResponseEntity<UserUpdateResponse> update(@RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserInfo(userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @ToException
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 할 때 사용하는 API")
    public ResponseEntity<Boolean> remove(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
