package com.m7m.omnitranslate.mapper;

import com.m7m.omnitranslate.entity.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    int insert(String userId,Long roleId);

}
