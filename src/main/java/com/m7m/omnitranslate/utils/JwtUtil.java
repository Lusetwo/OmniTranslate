package com.m7m.omnitranslate.utils;


import com.m7m.omnitranslate.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    //salt
    private static final String SECRET = JwtConfig.SECRET;
    //过期时间
    private static final long EXPIRATION = JwtConfig.EXPIRATION;

    /**
     * 生成 key
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成token
     * @param userId
     * @return
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 获取userId
     * @param token
     * @return
     */
    public String getUserId(String token) {
        Claims claims  = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 解析 token
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    /**
     * 是否过期
     */
    public boolean isExpired(String token) {
        Date expiration = parseToken(token).getExpiration();
        return expiration.before(new Date());
    }


}
