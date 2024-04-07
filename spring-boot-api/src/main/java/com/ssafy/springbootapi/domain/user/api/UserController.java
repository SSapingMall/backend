package com.ssafy.springbootapi.domain.user.api;

import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.dto.*;
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
    public ResponseEntity<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest userSignUpRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(userSignUpRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getInfo(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserInfo(id));
    }

    @PutMapping
    public ResponseEntity<UserUpdateResponse> update(@RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserInfo(userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
