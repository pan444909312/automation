package com.miller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.miller.mapper") //开启扫描mapper
public class WebApplication {

    public static void main(String[] args) {
        System.out.println("automation project starting...");
        SpringApplication.run(WebApplication.class, args);
    }

}
