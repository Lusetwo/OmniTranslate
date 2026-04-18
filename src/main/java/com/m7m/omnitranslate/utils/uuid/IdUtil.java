package com.m7m.omnitranslate.utils.uuid;

/**
 * @description: 随机Id生成器
 * @author: 22488
 * @date: 2026/04/11 14:59
 * @version: 1.0
 */

import java.util.concurrent.ThreadLocalRandom;

/**
 * ID生成器工具类
 *
 * @author dajue
 */
public class IdUtil
{
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 随机生成6位数id
     * @return
     */
    public static String getSixDigitId() {
        // 使用你已有的 UUID
        String uuid = UUID.fastUUID().toString(true);

        // 取 hashCode 保证随机性
        int hash = uuid.hashCode();

        // 转为正数
        hash = Math.abs(hash);

        // 取后 6 位
        int number = hash % 1000000;

        // 补零
        return String.format("%06d", number);
    }

    /**
     * 生成 long 型 ID (毫秒时间戳 + 3位随机数)
     * 适合低并发场景
     */
    public static long nextLongId() {
        // 当前时间的毫秒数 (13位)
        long timestamp = System.currentTimeMillis();
        // 生成 100 到 999 之间的随机数 (3位)
        int random = ThreadLocalRandom.current().nextInt(100, 1000);

        // 组合成 16位的 long 整数
        return timestamp * 1000 + random;
    }
}
