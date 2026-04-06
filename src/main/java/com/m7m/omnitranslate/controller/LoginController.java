package com.m7m.omnitranslate.controller;


import com.m7m.omnitranslate.dto.LoginDTO;
import com.m7m.omnitranslate.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO userDto) {
        return sysUserService.login(userDto.getUsername(), userDto.getPassword());
    }
}
