package com.m7m.omnitranslate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync           // 开启异步任务支持 (如: 异步处理大文本翻译)
@EnableScheduling      // 开启定时任务支持 (如: 定时清理过期翻译缓存)
@EnableCaching         // 开启缓存支持 (如: 缓存常用词汇翻译结果)
//@EnableRetry           // 开启重试机制支持 (如: 第三方API调用失败重试)
//@EnableFeignClients    // 开启OpenFeign客户端 (如: 声明式调用外部翻译API)
//@EnableDiscoveryClient // 开启服务发现 (如: 注册到Nacos)
@MapperScan("com.m7m.omnitranslate.mapper") // 扫描 MyBatis Mapper 接口
public class OmnitranslateApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmnitranslateApplication.class, args);
    }
}
