package com.m7m.omnitranslate.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    public static final String SECRET = "f84c3cbe2b4e1e6f8f4f1a9e6c8a7d9c9e3b6a1d4f2e8c7b5a9d1e3f6b8c2a4d";

    public static final long EXPIRATION = 7200000;
}
