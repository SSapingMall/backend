package com.ssafy.springbootapi.domain.user.dto;

import com.ssafy.springbootapi.domain.user.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class UserInfoResponse {
    private String email;
    private String name;
    private List<Address> addresses;
}
