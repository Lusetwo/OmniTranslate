package com.m7m.omnitranslate.security.filter;

import com.m7m.omnitranslate.entity.SysPermission;
import com.m7m.omnitranslate.entity.SysUser;
import com.m7m.omnitranslate.mapper.SysPermissionMapper;
import com.m7m.omnitranslate.mapper.SysUserMapper;
import com.m7m.omnitranslate.service.CustomUserDetailsService;
import com.m7m.omnitranslate.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token =  header.substring(7);

            try {
                String userId = jwtUtil.getUserId(token);

                SysUser user = sysUserMapper.findByUserId(userId);

                if (user == null) {
                    List<SysPermission> permissions = sysPermissionMapper.findPermissionsByUserId(userId);

                    List<GrantedAuthority> authorities = permissions.stream()
                            .map(p-> new SimpleGrantedAuthority(p.getPermissionCode()))
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
