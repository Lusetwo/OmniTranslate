package com.m7m.omnitranslate.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.Scanner;

public class AutoGeneratorUtil {

    // 数据库连接配置
    private static final String URL = "jdbc:mysql://localhost:3306/omni_translate?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qia_2020ismysweet";

    // 作者信息
    private static final String AUTHOR = "Lusetwo";

    public static void main(String[] args) {
        System.out.println("请输入需要生成的表名，多个表名用英文逗号分割 (例如: t_user,t_order):");
        Scanner scanner = new Scanner(System.in);
        String tables = scanner.nextLine();

        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 1. 全局配置 (GlobalConfig)
                .globalConfig(builder -> {
                    builder.author(AUTHOR)                  // 设置作者
                            .enableSwagger()                // 开启 swagger 模式，生成 @Schema 等注解
                            .fileOverride()                 // 覆盖已生成文件 (慎用，按需开启)
                            .disableOpenDir()               // 生成后不自动打开目录
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定 Java 输出目录
                })
                // 2. 包配置 (PackageConfig)
                .packageConfig(builder -> {
                    builder.parent("com.m7m")   // 设置父包名 (根据你的项目修改)
                            .moduleName("omnitranslate")           // 设置父包模块名 (可选)
                            .entity("entity")               // 实体类包名
                            .mapper("mapper")               // Mapper包名
                            .service("service")             // Service包名
                            .serviceImpl("service.impl")    // ServiceImpl包名
                            .controller("controller")       // Controller包名
                            // 单独配置 Mapper XML 的输出路径到 resources 下
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper/system"));
                })
                // 3. 策略配置 (StrategyConfig) - 核心配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables.split(","))   // 设置需要生成的表名
                            .addTablePrefix("tb_", "c_")     // 设置过滤表前缀 (生成类名时会自动去掉 t_)

                            // 实体类策略配置
                            .entityBuilder()
                            .enableLombok()                 // 开启 Lombok 注解 (@Data)
                            .enableChainModel()             // 开启链式模型 (@Accessors(chain = true))
                            .enableTableFieldAnnotation()   // 生成 @TableField 注解
                            .naming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel) // 表名转驼峰
                            .columnNaming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel) // 列名转驼峰
                            // .superClass(BaseEntity.class) // 如果你有公共的基类实体，可以取消注释

                            // Controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle()              // 开启 @RestController 风格

                            // Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 service 接口名 (默认会以 I 开头，这里去掉 I)

                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation();      // 开启 @Mapper 注解
                })
                // 4. 注入模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        System.out.println("代码生成完毕！");
    }
}