package com.miller.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import org.apache.ibatis.session.SqlSession;

public class AutoDBUtils {
    private static SqlSession sqlSessionOfAutomation;


    /**
     * 获取数据库链接 “panda_test" 库
     *
     * @return SqlSession
     */
    public static synchronized SqlSession getDBOfAutomationTest() {
        if (sqlSessionOfAutomation != null) return sqlSessionOfAutomation;
        var mySqlUrl = new PropertiesUtils().getProperty(AutoDBUtils.class, "spring.datasource.automation.url");
        var userName = new PropertiesUtils().getProperty(AutoDBUtils.class, "spring.datasource.automation.username");
        var passWord = new PropertiesUtils().getProperty(AutoDBUtils.class, "spring.datasource.automation.password");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfAutomation = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), AutoDBUtils.class);
        return sqlSessionOfAutomation;
    }
}
