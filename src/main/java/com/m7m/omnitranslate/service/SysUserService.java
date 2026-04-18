package com.m7m.omnitranslate.service;


import com.m7m.omnitranslate.common.Results;
import com.m7m.omnitranslate.dto.LoginDTO;
import com.m7m.omnitranslate.dto.RegisterDTO;
import com.m7m.omnitranslate.vo.UserInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
public interface SysUserService{

    //String login(String username, String password);

    Results login(LoginDTO loginDTO, HttpServletRequest request);

    void register(RegisterDTO dto);

    UserInfoVO getCurrentUserInfo(String token);

    void logout(String userId, HttpServletRequest request);
}
