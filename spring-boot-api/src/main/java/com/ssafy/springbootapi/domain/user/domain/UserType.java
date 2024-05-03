package com.ssafy.springbootapi.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    ADMIN("ADMIN"),
    BUYER("BUYER"),
    SELLER("SELLER");

    private String description;
}
