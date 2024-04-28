package com.ssafy.springbootapi.global.error;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String s) {
        super(s);
    }
}
