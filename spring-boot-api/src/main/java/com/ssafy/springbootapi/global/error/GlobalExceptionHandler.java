package com.ssafy.springbootapi.global.error;

 import com.ssafy.springbootapi.domain.product.exception.NotFoundProductException;
 import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler { // Domain별 Exception 핸들러

     @ExceptionHandler(NotFoundProductException.class)
     public ResponseEntity<?> handleNotFoundProductException(NotFoundProductException ex) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
     }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
