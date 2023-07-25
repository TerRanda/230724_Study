package com.thejoeun.practice1.boilerplate1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenDto {
    private String grantType;       //토큰 타입
    private String accessToken;     //접근  
    private Long tokenExpiresIn;    //유효기간
}
