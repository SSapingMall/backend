package com.ssafy.springbootapi.domain.user.exception;

public class UserDuplicatedException extends RuntimeException {
    public UserDuplicatedException(String s) {
        super(s);
    }
}
