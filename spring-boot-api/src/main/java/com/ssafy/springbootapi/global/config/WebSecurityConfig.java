package com.ssafy.springbootapi.global.config;

import com.ssafy.springbootapi.domain.user.domain.UserType;
import com.ssafy.springbootapi.global.auth.jsonAuthentication.JsonUsernamePasswordAuthenticationFilter;
import com.ssafy.springbootapi.global.auth.jsonAuthentication.JsonUserAuthenticationSuccessHandler;
import com.ssafy.springbootapi.global.auth.jsonAuthentication.JsonUserAuthenticationProvider;
import com.ssafy.springbootapi.global.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JsonUserAuthenticationProvider jsonUserAuthenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JsonUserAuthenticationSuccessHandler jsonUserAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // http basic 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        // 세션 관리 정책 설정
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // HTTP 요청에 대한 접근 제어
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/auth/token").permitAll()
//                .requestMatchers("/api/v1/login").permitAll()  // 로그인 API는 모든 요청을 허용
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()                 // 그 외의 모든 요청은 인증 필요
        );

        // jwt 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter
                ,UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(jsonUserAuthenticationProvider);

        // UsernamePasswordAuthenticationFilter 추가
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration));
        filter.setAuthenticationSuccessHandler(jsonUserAuthenticationSuccessHandler);
        filter.setFilterProcessesUrl("/api/v1/auth/login");
        http.addFilterAt(filter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    public JsonUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
//        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter(authenticationManager);
//        filter.setAuthenticationManager(authenticationManager);
//        filter.setFilterProcessesUrl("/api/v1/users/login");  // 필터가 실행될 URL 설정
//        return filter;
//    }

}
