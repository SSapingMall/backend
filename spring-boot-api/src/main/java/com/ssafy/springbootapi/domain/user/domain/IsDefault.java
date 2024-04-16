package com.ssafy.springbootapi.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDefault {
    Enabled("기본배송지"),
    Disabled("일반배송지");

    private String description;
}
