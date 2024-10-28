package com.miller.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

/**
 * @author panjuxiang
 * @since 2024/9/19 9:30
 */
public class CodeGenerator {

    @Test
    public void run() {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com:3306/automation_test", "automation", "20AR@UJsobwLBdih")
                .globalConfig(builder -> {
                    builder.author("panjuxiang")// 设置作者
                            .outputDir(projectPath + "/src/main/java") // 输出目录
//                            .enableSwagger()
                            .enableSpringdoc()
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent("com.miller") // 设置父包名
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl"); // 设置 Service 实现类包名
//                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("auto_case_roi_chart") // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle() // 启用 REST 风格
                            .serviceBuilder().formatServiceFileName("%sService");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
