package com.ssafy.springbootapi.global.error;

// import com.ssafy.springbootapi.domain.product.exception.NotFoundProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler { // Domain별 Exception 핸들러

    // @ExceptionHandler(NotFoundProductException.class)
    // public ResponseEntity<?> handleNotFoundProductException(NotFoundProductException ex) {
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    // }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
