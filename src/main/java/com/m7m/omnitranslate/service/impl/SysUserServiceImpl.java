package com.m7m.omnitranslate.service.impl;

import com.m7m.omnitranslate.entity.SysUser;
import com.m7m.omnitranslate.service.SysUserService;
import com.m7m.omnitranslate.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SysUser user = (SysUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user.getUsername());

        redisTemplate.opsForValue().set("login" + user.getUsername(),token ,2,TimeUnit.HOURS);

        return token;
    }
}
