package com.miller.userapp.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 数据库链接
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/24 17:47:09
 */
public class DBUtils {
    private static SqlSession sqlSessionOfPandaTest;
    private static SqlSession sqlSessionOfPandaPayTest;

    /**
     * 获取数据库链接 “panda_test" 库
     *
     * @return SqlSession
     */
    public static synchronized SqlSession getDBOfPandaTest() {
        if (sqlSessionOfPandaTest != null) return sqlSessionOfPandaTest;
        var mySqlUrl = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.url");
        var userName = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.username");
        var passWord = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.password");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfPandaTest = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), DBUtils.class);
        return sqlSessionOfPandaTest;
    }
    public static synchronized SqlSession getDBOfPandaPayTest() {
        if (sqlSessionOfPandaPayTest != null) return sqlSessionOfPandaPayTest;
        var mySqlUrl = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.pay.url");
        var userName = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.username");
        var passWord = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.password");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfPandaPayTest = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), DBUtils.class);
        return sqlSessionOfPandaPayTest;
    }
}
