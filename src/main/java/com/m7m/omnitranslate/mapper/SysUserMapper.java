package com.m7m.omnitranslate.mapper;

import com.m7m.omnitranslate.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectByUsername(String username);

    int insert(SysUser sysUser);

    SysUser findByUserId(String userId);
}
