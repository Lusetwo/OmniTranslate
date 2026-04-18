package com.m7m.omnitranslate.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVO {

    private String id;

    private String username;

    private String email;

    private String phone;

    private List<String> roles;

    private List<String> permissions;
}
