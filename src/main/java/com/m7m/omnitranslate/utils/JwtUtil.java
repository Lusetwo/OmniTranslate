package com.m7m.omnitranslate.utils;


import com.m7m.omnitranslate.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    //salt
    private static final String SECRET = JwtConfig.SECRET;
    //过期时间
    private static final long EXPIRATION = JwtConfig.EXPIRATION;

    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims  = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
