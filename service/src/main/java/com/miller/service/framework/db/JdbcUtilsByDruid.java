package com.miller.service.framework.db;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * JDBC的工具类，使用Druid数据库连接池
 *
 * @author Miller Shan
 * @version 1.0.0
 */
@Slf4j
public class JdbcUtilsByDruid {
    /**
     * 获取连接池对象
     *
     * @param driverClassName 驱动名称，比如MySQL为: com.mysql.jdbc.Driver
     * @param mySqlUrl        链接的数据库名称，比如: jdbc:mysql://127.0.0.1:3306/ct_dev
     * @param userName        账号
     * @param passWord        密码
     * @param initialSize     连接池初始化大小
     * @param maxActive       连接池最大数
     * @param maxWait         最长等待时间
     * @return 数据源对象
     */
    public DataSource getDataSource(String driverClassName, String mySqlUrl, String userName, String passWord,
                                    Integer initialSize, Integer maxActive, Long maxWait) {
        if (driverClassName == null || driverClassName.isBlank()) {
            log.error("The driverClassName can not be empty.");
            throw new RuntimeException("The driverClassName can not be empty.");
        }
        if (mySqlUrl == null || mySqlUrl.isBlank()) {
            log.error("The mySqlUrl can not be empty.");
            throw new RuntimeException("The mySqlUrl can not be empty.");
        }
        if (userName == null || userName.isBlank()) {
            log.error("The userName can not be empty.");
            throw new RuntimeException("The userName can not be empty.");
        }
        if (passWord == null || passWord.isBlank()) {
            log.error("The passWord can not be empty.");
            throw new RuntimeException("The passWord can not be empty.");
        }
        if (initialSize == null || initialSize < 0) {
            log.error("The initialSize can not be empty or < 0.");
            throw new RuntimeException("The initialSize can not be empty or < 0.");
        }
        if (maxActive == null || maxActive <= 0) {
            log.error("The maxActive can not be empty or <= 0.");
            throw new RuntimeException("The maxActive can not be empty or <= 0.");
        }
        if (maxWait == null || maxWait <= 0) {
            log.error("The maxWait can not be empty or <= 0.");
            throw new RuntimeException("The maxWait can not be empty or <= 0.");
        }
        // 创建数据库的连接池对象
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(mySqlUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(passWord);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }

    /**
     * 获取连接池对象
     *
     * @param mySqlUrl 链接的数据库名称，比如: jdbc:mysql://127.0.0.1:3306/ct_dev
     * @param userName 账号
     * @param passWord 密码
     * @return 数据源对象
     * @see #getDataSource(String, String, String, String, Integer, Integer, Long)
     */
    public DataSource getDataSource(String mySqlUrl, String userName, String passWord) {
        return getDataSource("com.mysql.cj.jdbc.Driver", mySqlUrl, userName, passWord,
                0, 8, 3000L);
    }
}
