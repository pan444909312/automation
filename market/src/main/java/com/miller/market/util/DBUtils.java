package com.miller.market.util;

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
    private static SqlSession sqlSessionOfFreshTest;


    /**
     * 获取数据库链接 “fresh_test" 库
     *
     * @return SqlSession
     */
    public static synchronized SqlSession getDBOfFreshTest() {
        if (sqlSessionOfFreshTest != null) return sqlSessionOfFreshTest;
        var mySqlUrl = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.url");
        var userName = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.username");
        var passWord = new PropertiesUtils().getProperty(DBUtils.class, "spring.datasource.password");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfFreshTest = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), DBUtils.class);
        return sqlSessionOfFreshTest;
    }
}
