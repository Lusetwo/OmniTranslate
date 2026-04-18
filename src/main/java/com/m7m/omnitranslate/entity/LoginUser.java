package com.m7m.omnitranslate.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @description: TODO
 * @author: 22488
 * @date: 2026/04/11 16:01
 * @version: 1.0
 */
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private SysUser sysUser;

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // 先不做权限
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     * 账号是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否启用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}