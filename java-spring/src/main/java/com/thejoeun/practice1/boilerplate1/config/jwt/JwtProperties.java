package com.thejoeun.practice1.boilerplate1.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
