package com.m7m.omnitranslate.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    public static final String SECRET = "1234567890abcdefghijklmnopqrstuvwsyz";

    public static final long EXPIRATION = 7200000;
}
