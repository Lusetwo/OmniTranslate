package com.m7m.omnitranslate.service;

import com.m7m.omnitranslate.entity.LoginUser;
import com.m7m.omnitranslate.entity.SysPermission;
import com.m7m.omnitranslate.entity.SysUser;
import com.m7m.omnitranslate.mapper.SysPermissionMapper;
import com.m7m.omnitranslate.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = userMapper.selectByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 查询权限
        List<SysPermission> permissions = sysPermissionMapper.findPermissionsByUserId(user.getUserId());

        // 转换成 Spring Security 权限
        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermissionCode()))
                .collect(Collectors.toList());

        return new LoginUser(user);

    }
}
