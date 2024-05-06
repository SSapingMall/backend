package com.ssafy.springbootapi.domain.product.exception;

public class NotFoundCategoryException extends RuntimeException{
    public NotFoundCategoryException(String message) {
        super(message);
    }
}
