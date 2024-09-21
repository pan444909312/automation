package com.miller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author panjuxiang
 * @since 2024/9/19 10:13
 */
@Configuration
public class Config implements WebMvcConfigurer {
    /**
     * 重写WebMvcConfigurer实现全局跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 是否发送Cookie,该项为true的时候，allowedOrigins不可为*，需要指定对应域名
                .allowCredentials(false)
                // 放行哪些原始域
                .allowedOrigins("*")
                // 放行哪些请求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 放行哪些原始请求头部信息
                .allowedHeaders("*");
    }
}
