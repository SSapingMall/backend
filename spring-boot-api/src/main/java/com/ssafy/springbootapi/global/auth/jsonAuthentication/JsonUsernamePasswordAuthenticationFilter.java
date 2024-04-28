package com.ssafy.springbootapi.global.auth.jsonAuthentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.springbootapi.domain.user.dto.UserLoginRequest;
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

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("In JsonUsernamePasswordAuthenticationFilter");
        if ("application/json".equals(request.getContentType())) {
            try {
                // JSON 요청에서 사용자 이름과 비밀번호를 읽어옵니다.
                BufferedReader reader = request.getReader();
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                UserLoginRequest loginRequest = objectMapper.readValue(sb.toString(), UserLoginRequest.class);
                setPasswordParameter("useremail "+loginRequest.getEmail());
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword());

                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // 기본 form-urlencoded 방식 처리
            return super.attemptAuthentication(request, response);
        }
    }

}
