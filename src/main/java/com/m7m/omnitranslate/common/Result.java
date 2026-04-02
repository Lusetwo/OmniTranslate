package com.m7m.omnitranslate.common;

/**
 * 全局统一返回结果类
 * 使用泛型 <T> 以支持返回任意类型的数据
 */
public class Result<T> {

    private Integer code;    // 状态码
    private String message;  // 提示信息
    private T data;          // 具体的返回数据

    // 私有化构造方法，强制使用提供的静态工厂方法
    private Result() {}

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ================= 成功响应的快捷方法 =================

    /**
     * 成功：不带数据
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功：带返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功：自定义提示信息和数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    // ================= 失败响应的快捷方法 =================

    /**
     * 失败：默认系统异常
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getMessage(), null);
    }

    /**
     * 失败：使用枚举定义错误状态
     */
    public static <T> Result<T> error(ResultCodeEnum resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 失败：自定义错误码和信息 (备用)
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}