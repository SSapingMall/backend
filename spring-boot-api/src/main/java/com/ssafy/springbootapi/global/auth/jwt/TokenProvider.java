package com.ssafy.springbootapi.global.auth.jwt;

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
import java.util.UUID;

/**
 * JWT 토큰을 관리하는 서비스 클래스.
 */
@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailService userDetailService;

    /**
     * 지정된 사용자 ID와 만료 기간을 기반으로 JWT 토큰을 생성합니다.
     *
     * @param id 사용자의 고유 UUID
     * @param expiredAt 토큰의 만료 시간
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(UUID id, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), id);
    }

    /**
     * 주어진 만료 일자와 사용자 ID로 JWT 토큰을 생성합니다.
     *
     * @param expiry 토큰의 만료 시간
     * @param id 사용자의 UUID
     * @return 생성된 JWT 토큰
     */
    private String makeToken(Date expiry, UUID id) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)                       // iat: 발급시간
                .setExpiration(expiry)                  // exp: 만료시간
                .setSubject(String.valueOf(id))            // sub: 유저 UUID
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    /**
     * 제공된 JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증하고자 하는 JWT 토큰
     * @return 토큰의 유효성 여부를 boolean으로 반환
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 유효한 JWT 토큰으로부터 사용자의 인증 정보를 가져옵니다.
     *
     * @param token 사용자의 JWT 토큰
     * @return 인증 정보 객체
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String uuid = claims.getSubject();
        UserDetails user = userDetailService.loadUserByUsername(uuid);

        return new UsernamePasswordAuthenticationToken(uuid, null, user.getAuthorities());
    }

    /**
     * JWT 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token 사용자의 JWT 토큰
     * @return 추출된 사용자 ID
     */
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    /**
     * 주어진 토큰에서 JWT 클레임 세트를 반환합니다.
     *
     * @param token JWT 토큰
     * @return 클레임 객체
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
