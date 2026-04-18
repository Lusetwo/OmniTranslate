package com.m7m.omnitranslate.controller;

import com.m7m.omnitranslate.common.Results;
import com.m7m.omnitranslate.dto.LoginDTO;
import com.m7m.omnitranslate.dto.RegisterDTO;
import com.m7m.omnitranslate.service.SysUserService;
import com.m7m.omnitranslate.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Results login(@RequestBody LoginDTO userDto,HttpServletRequest request) {
//        String login = sysUserService.login(userDto.getUsername(), userDto.getPassword());
        return sysUserService.login(userDto,request);
        //return Results.success("登录成功");
    }
    
    @PostMapping("/register")
    public Results register(@RequestBody RegisterDTO userDto) {
        sysUserService.register(userDto);

        return Results.success("注册成功");
    }

    @GetMapping("/me")
    public Results me(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null) {
            return Results.error(50001,"登录失败");
        }

        String token = header.substring(7);

        UserInfoVO currentUserInfo = sysUserService.getCurrentUserInfo(token);

        return Results.success(currentUserInfo);
    }
}
