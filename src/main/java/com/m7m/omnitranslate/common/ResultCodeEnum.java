package com.m7m.omnitranslate.common;

/**
 * 统一状态码枚举
 */
public enum ResultCodeEnum {
    // --- 通用状态 ---
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统内部异常"),
    PARAM_ERROR(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权或签名错误"),
    USER_NOT_EXIST(2001,"用户不存在"),
    PASSWORD_ERROR(2002,"用户名或密码错误"),
    USER_ROLE_NOT_EXIST(2003,"默认用户不存在"),
    USER_IS_EXIST(2004,"用户已存在"),

    // --- 业务专属状态 (悬浮翻译项目) ---
    IMAGE_EMPTY(4001, "接收到的图片流为空"),
    OCR_NO_TEXT(4002, "未识别到任何文字"),
    OCR_API_ERROR(4003, "OCR引擎识别异常"),
    TRANSLATE_API_ERROR(4004, "调用翻译服务异常");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
