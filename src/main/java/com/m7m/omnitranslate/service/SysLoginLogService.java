package com.m7m.omnitranslate.service;

import com.m7m.omnitranslate.entity.SysLoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
public interface SysLoginLogService {

    void recordSuccess(String userId, String username, String ip, String token, HttpServletRequest request);

    void recordFail(String username, String reason, HttpServletRequest request);
}
