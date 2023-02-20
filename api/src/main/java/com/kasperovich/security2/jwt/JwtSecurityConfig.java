package com.kasperovich.security2.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties("jwt")
@Data
public class JwtSecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;
}
