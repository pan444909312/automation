package com.miller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        System.out.println("automation project starting...");
        SpringApplication.run(WebApplication.class, args);
    }

}
