package com.ssafy.springbootapi.global.auth.jwt;

import com.ssafy.springbootapi.domain.user.domain.User;
import com.ssafy.springbootapi.global.auth.SecurityUser.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailService userDetailService;

    public String generateToken(String email, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime()+ expiredAt.toMillis()),email);
    }

    // jwt token 생성
    private String makeToken(Date expiry, String email){
        Date now = new Date();

        UserDetails user = userDetailService.loadUserByUsername(email);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)                       // iat: 발급시간
                .setExpiration(expiry)                  // exp: 만료시간
                .setSubject(user.getUsername())            // sub: 유저 이메일
//                .claim("id", user.get())            // 클레임 id
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드 (토큰 유효성 검사 이후 실행)
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        String userEmail = claims.getSubject();
        UserDetails user = userDetailService.loadUserByUsername(userEmail);

        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id",Long.class);
    }

    // 클래임 조회
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
