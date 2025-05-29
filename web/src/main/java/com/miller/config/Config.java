package com.miller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author panjuxiang
 * @since 2024/9/19 10:13
 */
@Configuration
public class Config implements WebMvcConfigurer {
//    private static final Logger log = LoggerFactory.getLogger(Config.class);
//
//    /**
//     * 重写WebMvcConfigurer实现全局跨域配置
//     *
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        log.info("开启全局跨域配置！！！！！！！！！！！1");
//        registry.addMapping("/**")
//                // 是否发送Cookie,该项为true的时候，allowedOrigins不可为*，需要指定对应域名
//                .allowCredentials(true)
//                // 放行哪些原始域
//                .allowedOrigins(
//                        "http://10.1.5.45:9527",
//                        "http://127.0.0.1:9527",
//                        "http://127.0.0.1:9001",
//                        "http://127.0.0.1:9080",
//                        "http://10.1.5.45:9001")
//                // 放行哪些请求方式
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                // 放行哪些原始请求头部信息
//                .allowedHeaders("*")
//                .maxAge(3600);
//    }
}
