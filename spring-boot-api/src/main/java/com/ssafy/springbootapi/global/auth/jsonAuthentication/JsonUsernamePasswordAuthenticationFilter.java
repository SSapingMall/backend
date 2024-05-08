package com.ssafy.springbootapi.global.auth.jsonAuthentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.dto.UserLoginRequest;
import com.ssafy.springbootapi.global.error.JsonProcessingAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * Json content type 의 요청에 대해 로그인 필터 진행
     * Json 의 ID와 Password 를 추출
     * 인증 매니저에게 ID와 Password 에 대한 인증 객체 (Authentication) 을 넘기며 인증 진행
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getContentType().startsWith("application/json")) {
            throw new JsonProcessingAuthenticationException("Authentication method not supported: " + request.getMethod());
        }

        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            UserLoginRequest loginRequest = objectMapper.readValue(sb.toString(), UserLoginRequest.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword());
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new JsonProcessingAuthenticationException("JSON parsing failed", e);
        }
    }


}
