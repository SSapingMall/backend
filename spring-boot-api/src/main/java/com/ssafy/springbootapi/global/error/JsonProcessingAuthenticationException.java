package com.ssafy.springbootapi.global.error;

import org.springframework.security.core.AuthenticationException;

public class JsonProcessingAuthenticationException extends AuthenticationException {
    public JsonProcessingAuthenticationException(String msg) {
        super(msg);
    }

    public JsonProcessingAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
