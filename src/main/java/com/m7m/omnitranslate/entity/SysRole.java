package com.m7m.omnitranslate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("role_name")
    private String roleName;

    @TableField("role_code")
    private String roleCode;

    @TableField("description")
    private String description;

    @TableField("status")
    private Byte status;

    @TableField("create_time")
    private LocalDateTime createTime;
}
