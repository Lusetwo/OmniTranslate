package com.m7m.omnitranslate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user")
//@ApiModel(value = "SysUser对象", description = "")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("user_id")
    private String userId;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("avatar")
    private String avatar;

    @TableField("status")
    private Integer status;

    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("deleted")
    private Integer deleted;
}
