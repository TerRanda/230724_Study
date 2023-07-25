package com.thejoeun.practice1.boilerplate1.jwt;

import com.thejoeun.practice1.boilerplate1.config.jwt.JwtTokenProvider;
import com.thejoeun.practice1.boilerplate1.model.dto.JwtTokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @DisplayName("JWT TOKEN MADE")
    @Test
    void jwtGenerateTest1() {
       JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDto("1");
        System.out.println("access token: " + jwtTokenDto.getAccessToken());
        System.out.println("grant type: " + jwtTokenDto.getGrantType());
        //unix time 읽기 쉬운 형태로 변경
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(jwtTokenDto.getTokenExpiresIn()),
                        TimeZone.getDefault().toZoneId());
        System.out.println("access token expire time: " + localDateTime);

    }
}
