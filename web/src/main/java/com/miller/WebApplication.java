package com.miller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.miller.userapp.mapper", "com.miller.mapper"}) //开启扫描mapper
@EnableScheduling //开启定时任务
public class WebApplication {

    public static void main(String[] args) {
        System.out.println("automation project starting...");
        SpringApplication.run(WebApplication.class, args);
    }

}
