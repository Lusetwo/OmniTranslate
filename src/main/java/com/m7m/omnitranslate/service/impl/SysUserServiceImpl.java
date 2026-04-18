package com.m7m.omnitranslate.service.impl;

import com.m7m.omnitranslate.common.ResultCodeEnum;
import com.m7m.omnitranslate.common.Results;
import com.m7m.omnitranslate.dto.LoginDTO;
import com.m7m.omnitranslate.dto.RegisterDTO;
import com.m7m.omnitranslate.entity.SysPermission;
import com.m7m.omnitranslate.entity.SysRole;
import com.m7m.omnitranslate.entity.SysUser;
import com.m7m.omnitranslate.mapper.*;
import com.m7m.omnitranslate.service.SysLoginLogService;
import com.m7m.omnitranslate.service.SysUserService;
import com.m7m.omnitranslate.utils.IpUtil;
import com.m7m.omnitranslate.utils.JwtUtil;
import com.m7m.omnitranslate.utils.uuid.IdUtil;
import com.m7m.omnitranslate.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lusetwo
 * @since 2026-04-03
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Autowired
    private SysLoginLogService loginLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
//    public String login(String username, String password) {
//        Authentication authentication;
//
//        try {
//            // 1. 尝试进行身份验证
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//        } catch (BadCredentialsException e) {
//            // 2. 规范日志记录：使用 log.warn 记录业务异常，而不是 System.out
//            log.warn("用户登录失败，密码错误。用户名: {}", username);
//            // 3. 规范异常处理：抛出业务异常，交由全局异常处理器（@RestControllerAdvice）统一封装给前端
//            throw new RuntimeException("用户名或密码错误");
//            // 注：建议替换为你项目自定义的异常类，例如 throw new BusinessException(400, "用户名或密码错误");
//        } catch (AuthenticationException e) {
//            log.warn("用户登录失败，账号状态异常。用户名: {}, 原因: {}", username, e.getMessage());
//            throw new RuntimeException("登录失败：" + e.getMessage());
//        } catch (Exception e) {
//            // 4. 未知异常使用 log.error 记录堆栈信息，方便查错
//            log.error("用户登录时发生未知异常。用户名: {}", username, e);
//            throw new RuntimeException("系统繁忙，请稍后再试");
//        }
//
//        // 5. 验证成功，获取上下文用户信息
//        LoginUser user = (LoginUser) authentication.getPrincipal();
//        log.info("用户登录成功。用户名: {}", user.getUsername());
//
//        // 6. 生成 JWT
//        String token = jwtUtil.generateToken(user.getUsername());
//
//        // 7. 将 Token 存入 Redis，统一管理魔法值（可选：将过期时间 2 提取为常量）
//        redisTemplate.opsForValue().set("login:" + user.getUsername(), token, 2, TimeUnit.HOURS);
//
//        return token;
//    }

    @Override
    public Results login(LoginDTO loginDTO, HttpServletRequest request) {
        SysUser user = userMapper.selectByUsername(loginDTO.getUsername());

        // 1. 判断用户是否存在
        if (user == null) {
            loginLogService.recordFail(loginDTO.getUsername(), "USER_NOT_EXIST", request);
            return Results.error(ResultCodeEnum.USER_NOT_EXIST);
        }

        // 2. 校验密码
        if (!passwordMatches(loginDTO.getPassword(), user.getPassword())) {
            loginLogService.recordFail(user.getUsername(), "PASSWORD_ERROR", request);
            return Results.error(ResultCodeEnum.PASSWORD_ERROR);
        }

        // 3. 登录成功，生成 Token
        String token = jwtUtil.generateToken(user.getUserId());

        // 记录成功日志
        loginLogService.recordSuccess(user.getUserId(), user.getUsername(), IpUtil.getIp(request), token, request);

        return Results.success(token);
    }

    private boolean passwordMatches(String password, String confirmPassword) {
        return passwordEncoder.matches(password, confirmPassword);
    }

    @Override
    @Transactional
    public void register(RegisterDTO dto) {

        // 检查用户是否存在
        SysUser exist = userMapper.selectByUsername(dto.getUsername());

        if (exist != null) {
            throw new RuntimeException(ResultCodeEnum.USER_IS_EXIST.getMessage());
        }

        //密码加密
        String encodePassword = passwordEncoder.encode(dto.getPassword());

        //创建用户
        SysUser user = new SysUser();
        user.setId(IdUtil.fastSimpleUUID());
        user.setUserId(IdUtil.getSixDigitId());
        user.setUsername(dto.getUsername());
        user.setPassword(encodePassword);
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus(0);
        user.setDeleted(1);
        userMapper.insert(user);

        //查询默认角色
        SysRole role = roleMapper.findByCode("USER");

        if (role == null) {
            throw new RuntimeException(ResultCodeEnum.USER_NOT_EXIST.getMessage());
        }

        //绑定角色
        userRoleMapper.insert(user.getUserId(),role.getId());
    }

    @Override
    public UserInfoVO getCurrentUserInfo(String token) {
        // 解析token
        String userId = jwtUtil.getUserId(token);

        //查询用户
        SysUser user = userMapper.findByUserId(userId);

        if (user == null) {
            throw new RuntimeException(ResultCodeEnum.USER_NOT_EXIST.getMessage());
        }

        //查询角色
        List<SysRole> roles = roleMapper.findByUserId(userId);

        //查询权限
        List<SysPermission> permissions = permissionMapper.findPermissionsByUserId(userId);

        //转换
        UserInfoVO vo = new UserInfoVO();
        vo.setId(user.getUserId());
        vo.setUsername(user.getUsername());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setRoles(roles.stream().map(SysRole::toString).collect(Collectors.toList()));
        vo.setPermissions(permissions.stream().map(SysPermission::toString).collect(Collectors.toList()));

        return vo;
    }

    @Override
    public void logout(String userId, HttpServletRequest request) {

        redisTemplate.delete("refresh_token:" + userId);

        SysUser user = userMapper.findByUserId(userId);
        //添加登出日志
        loginLogService.recordFail(user.getUsername(),"用户登出",request);

    }



    public static void main(String[] args) {
        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String dbPassword =
                "$2a$10$Y1DOC8K53rvbzcTHjIcAVOXwc.COrKXtGePd4YELpfJ60Cq7wx/mm";

        boolean match =
                encoder.matches("123456", dbPassword);

        System.out.println(match);
    }
}
