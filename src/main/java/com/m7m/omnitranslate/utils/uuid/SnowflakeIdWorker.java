package com.m7m.omnitranslate.utils.uuid;

/**
 * @description: 雪花算法
 * @author: 22488
 * @date: 2026/04/11 15:10
 * @version: 1.0
 */
public class SnowflakeIdWorker {

    /**
     *
     * 开始时间戳
     */
    private static final long START_TIMESTAMP = 1704067200000L;

    /**
     *
     * 各部分占用位数
     */
    private static final long SEQUENCE_BIT = 12L;
    private static final long MACHINE_BIT = 5L;
    private static final long DATACENTER_BIT = 5L;

    /**
     *
     * 最大值
     */
    private static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     *
     * 偏移量
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_BIT + MACHINE_BIT;

    /**
     * 数据中心
     */
    private final long datacenterId;

    /**
     * 机器ID
     */
    private final long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上次时间戳
     */
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker(long datacenterId, long machineId) {

        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException(
                    "datacenterId can't be greater than " + MAX_DATACENTER_NUM
            );
        }

        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException(
                    "machineId can't be greater than " + MAX_MACHINE_NUM
            );
        }

        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成ID（线程安全）
     */
    public synchronized long nextId() {

        long currentTimestamp = getCurrentTimestamp();

        // 时钟回拨保护
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException(
                    "Clock moved backwards. Refusing to generate id"
            );
        }

        if (currentTimestamp == lastTimestamp) {

            sequence = (sequence + 1) & MAX_SEQUENCE;

            if (sequence == 0) {
                currentTimestamp = getNextMill();
            }

        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {

        long mill = getCurrentTimestamp();

        while (mill <= lastTimestamp) {
            mill = getCurrentTimestamp();
        }

        return mill;
    }

    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

}
