package com.thejoeun.practice1.boilerplate1.config.jwt;



import com.thejoeun.practice1.boilerplate1.model.dto.JwtTokenDto;
import com.thejoeun.practice1.boilerplate1.model.enums.Authority;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    //    private final Key key;
    private String jwtSecretKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);  // jdk8에 있나 모르겠음...
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtSecretKey = secretKey;
    }

    // 토큰 생성 by authentication
    public JwtTokenDto generateTokenDto(String id) {
        Date expireDate = getTokenExpirationTime();

        String accessToken = Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
//                .setIssuer(jwtProperties.getIssuer())
//                .setIssuedAt(now)
                .setExpiration(expireDate)
                .setSubject(id)
//                .claim("email", email)
//                .claim(AUTHORITIES_KEY, email)
                .claim(AUTHORITIES_KEY, Authority.ROLE_USER.name())
//                .setSubject(member.getEmail())
//                .claim("id", member.getId())
//                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();

        return JwtTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(expireDate.getTime())
                .build();
    }

    // 토큰 생성 by authentication
    public JwtTokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expireDate = getTokenExpirationTime();
        String name = authentication.getName();

        String accessToken = Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(expireDate)
//                .signWith(key, SignatureAlgorithm.HS512)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();

        return JwtTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(expireDate.getTime())
                .build();
    }

    private Date getTokenExpirationTime() {
        long now = (new Date()).getTime();
        return new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = null;
        try {
            claims = parseClaims(accessToken);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        }

        if (Objects.isNull(claims)
                || Objects.isNull(claims.get(AUTHORITIES_KEY))
//            || !(Objects.nonNull(claims.get(AUTHORITIES_KEY)) || Objects.nonNull(claims.get(AUTHORITIES_KEY)))
        ) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = getClaims(token);
//        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return new UsernamePasswordAuthenticationToken(
//                    new User(claims.getSubject(), "", authorities),
//                    token,
//                    authorities);
//    }
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
//                .setSigningKey(key)
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(accessToken)
                .getBody();
    }
}