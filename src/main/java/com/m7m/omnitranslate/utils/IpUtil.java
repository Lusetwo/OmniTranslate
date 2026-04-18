package com.m7m.omnitranslate.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: TODO
 * @author: 22488
 * @date: 2026/04/18 14:03
 * @version: 1.0
 */
public class IpUtil {

    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
