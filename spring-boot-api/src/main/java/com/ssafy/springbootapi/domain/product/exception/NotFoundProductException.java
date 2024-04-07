package com.ssafy.springbootapi.domain.product.exception;

public class NotFoundProductException extends RuntimeException{
    public NotFoundProductException(String message) {
        super(message);
    }
}
