package com.m7m.omnitranslate.controller;

import com.m7m.omnitranslate.common.Results;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@RestController
@RequestMapping("/omnitranslate/sysUser")
public class SysUserController {

        @GetMapping("/list")
        @PreAuthorize("hasAuthority('user:view')")
        public Results list() {


            return Results.success("用户列表");

        }

        @PostMapping("/add")
        @PreAuthorize("hasAuthority('user:add')")
        public Results add() {

            return Results.success("新增用户成功");

        }

        @DeleteMapping("/delete")
        @PreAuthorize("hasAuthority('user:delete')")
        public Results delete() {

            return Results.success("删除用户成功");

        }

    }
