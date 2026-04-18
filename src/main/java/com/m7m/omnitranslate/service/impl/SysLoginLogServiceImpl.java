package com.m7m.omnitranslate.service.impl;

import com.m7m.omnitranslate.entity.SysLoginLog;
import com.m7m.omnitranslate.mapper.SysLoginLogMapper;
import com.m7m.omnitranslate.service.SysLoginLogService;
import com.m7m.omnitranslate.utils.uuid.IdUtil;
import com.m7m.omnitranslate.utils.uuid.SnowflakeIdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.m7m.omnitranslate.utils.IpUtil.getIp;
import static com.m7m.omnitranslate.utils.UserAgentUtil.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl implements SysLoginLogService {

    private final SysLoginLogMapper loginLogMapper;

    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public void recordSuccess(String userId, String username, String ip, String token, HttpServletRequest request) {

        SysLoginLog log = new SysLoginLog();
        log.setId(IdUtil.nextLongId());
        log.setUserId(userId);
        log.setUsername(username);
        log.setIp(ip);
        log.setDevice(getDevice(request));
        log.setBrowser(getBrowser(request));
        log.setOs(getOs(request));
        log.setStatus(0);
        log.setLoginTime(LocalDateTime.now());
        loginLogMapper.insert(log);
    }

    @Override
    public void recordFail(String username, String reason, HttpServletRequest request) {

        SysLoginLog log = new SysLoginLog();

        log.setId(Long.valueOf(IdUtil.simpleUUID()));

        log.setUsername(username);

        log.setLoginTime(LocalDateTime.now());

        log.setIp(getIp(request));

        log.setStatus(1);

        log.setReason(reason);

        log.setLoginTime(LocalDateTime.now());

        loginLogMapper.insert(log);

    }

}
