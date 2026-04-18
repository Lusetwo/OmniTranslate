package com.m7m.omnitranslate.mapper;

import com.m7m.omnitranslate.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole findByCode(String code);

    List<SysRole> findByUserId(String userId);
}
