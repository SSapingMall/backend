package com.ssafy.springbootapi.domain.user.api;

import com.ssafy.springbootapi.domain.user.application.UserService;
import com.ssafy.springbootapi.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserSignUpResponseDTO> signUp(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(userSignUpRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponseDTO> getInfo(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserInfo(id));
    }

    @PutMapping
    public ResponseEntity<UserUpdateResponseDTO> update(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserInfo(userUpdateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
