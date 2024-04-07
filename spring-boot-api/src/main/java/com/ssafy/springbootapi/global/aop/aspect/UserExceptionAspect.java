package com.ssafy.springbootapi.global.aop.aspect;

import com.ssafy.springbootapi.domain.user.exception.UserNotFoundException;
import com.ssafy.springbootapi.global.aop.annotation.ToException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;

@Aspect
public class UserExceptionAspect {

    @Around(value = "@annotation(ToException)")
    public ResponseEntity<?> exceptionHandler(ProceedingJoinPoint joinPoint, ToException ToException) {
        try {
            // 원래 메서드 실행
            return (ResponseEntity<?>)joinPoint.proceed();
        } catch (Throwable ex) {
            // 예외 처리 로직
            // 여기서 ResponseEntity 객체를 반환할 수 있음
            return ResponseEntity.badRequest().body("An error occurred: " + ex.getMessage());
        }
    }
}
