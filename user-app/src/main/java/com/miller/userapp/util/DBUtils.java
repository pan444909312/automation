package com.miller.userapp.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.ApplicationPropertiesUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 数据库链接
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 17:47:09
 */
public class DBUtils {

    /**
     * 获取数据库链接 “panda_test" 库
     *
     * @return SqlSession
     */
    public static SqlSession getDBOfPandaTest() {
        String mySqlUrl = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.url");
        String userName = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.username");
        String passWord = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.password");
        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
        return myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
    }
}
