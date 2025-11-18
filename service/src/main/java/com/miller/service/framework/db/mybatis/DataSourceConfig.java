package com.miller.service.framework.db.mybatis;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;

/**
 * 通过代码配置HikariDataSource
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/29 15:40:57
 */
@Data
public class DataSourceConfig {
    private DataSource DataSource;

    public DataSourceConfig(String jdbcUrl, String userName, String passWord) {
        this.DataSource = getDataSource("com.mysql.cj.jdbc.Driver", jdbcUrl, userName, passWord);
    }

    public DataSourceConfig(String driverClassName, String jdbcUrl, String userName, String passWord) {
        this.DataSource = getDataSource(driverClassName, jdbcUrl, userName, passWord);
    }

    private @NotNull HikariDataSource getDataSource(String driverClassName, String jdbcUrl, String userName, String passWord) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(passWord);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(60000 * 10);
        config.setAllowPoolSuspension(true);
        config.setConnectionTestQuery("SELECT 1");
        config.setKeepaliveTime(300000);  // 每5分钟进行一次心跳检查，避免空闲被断开的情况
        // 其他配置...
        return new HikariDataSource(config);
    }
}