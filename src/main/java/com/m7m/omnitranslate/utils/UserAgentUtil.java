package com.m7m.omnitranslate.utils;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @author: 22488
 * @date: 2026/04/18 14:25
 * @version: 1.0
 */
public class UserAgentUtil {

    // 声明为静态常量，确保全局唯一
    private static final UserAgentAnalyzer uaa;

    static {
        uaa = UserAgentAnalyzer.newBuilder()
                .hideMatcherLoadStats() // 隐藏启动时的规则加载日志，保持控制台干净
                .withCache(10000)       // 开启LRU缓存，缓存最近解析过的 10000 个 UA，极大提升并发性能
                .build();
    }

    /**
     *
     * 获取浏览器信息
     */
    /**
     * 获取浏览器信息 (如: Chrome 114, Safari 16.5)
     */
    public static String getBrowser(HttpServletRequest request) {
        String uaString = request.getHeader("User-Agent");
        if (uaString == null || uaString.isEmpty()) {
            return "Unknown";
        }

        UserAgent parsedAgent = uaa.parse(uaString);

        // 直接获取 浏览器名称 + 主版本号 (最常用)
        return parsedAgent.getValue(UserAgent.AGENT_NAME_VERSION_MAJOR);
    }


    /**
     * 获取设备信息 (如: Phone - Apple iPhone, Desktop)
     */
    public static String getDevice(HttpServletRequest request) {
        String osString = request.getHeader("User-Agent");

        if (osString == null || osString.isEmpty()) {
            return "Unknown";
        }

        UserAgent parsedAgent = uaa.parse(osString);

        //获取设备大类
        String deviceClass = parsedAgent.getValue(UserAgent.DEVICE_CLASS);
        //获取具体品牌名称
        String deviceName = parsedAgent.getValue(UserAgent.DEVICE_NAME);

        return deviceClass + " " + deviceName;
    }

    /**
     * 获取操作系统信息 (如: Windows NT 10.0, iOS 16.5, Android 13)
     */
    public static String getOs(HttpServletRequest request) {
        String uaString = request.getHeader("User-Agent");
        if (uaString == null || uaString.isEmpty()) {
            return "Unknown";
        }

        UserAgent parsedAgent = uaa.parse(uaString);

        // 直接获取 操作系统名称 + 版本号
        return parsedAgent.getValue(UserAgent.OPERATING_SYSTEM_NAME_VERSION);
    }
}
