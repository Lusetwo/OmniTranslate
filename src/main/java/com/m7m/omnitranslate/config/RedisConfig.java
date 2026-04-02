package com.m7m.omnitranslate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory  factory) {
        // 创建 RedisTemplate 实例，它是 Spring 提供的操作 Redis 的核心工具类
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 1. 设置连接工厂：告诉 Template 应该连接哪台 Redis 服务器（由配置文件 application.yml 提供参数）
        template.setConnectionFactory(factory);

        // 2. 设置 Key 的序列化方式：
        // 使用 StringRedisSerializer，这会让 Redis 中的 key 以普通字符串形式保存（而不是乱码）
        template.setKeySerializer(new StringRedisSerializer());

        // 3. 设置 Value 的序列化方式：
        // 使用 Jackson 库将 Java 对象序列化为标准的 JSON 字符串保存到 Redis
        // 这样在 Redis 客户端里你可以直接读到 {"id":1, "name":"Wise"} 这样的清晰数据
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
