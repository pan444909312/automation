package com.miller.service.framework.report;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 获取数据库链接
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/11/29 17:01:01
 */
public class AutoDBUtils {
    private static SqlSession sqlSessionOfAutomation;

    /**
     * 获取数据库链接 “automation" 库
     *
     * @return SqlSession
     */
    public static synchronized SqlSession getDBOfAutomationTest() {
        if (sqlSessionOfAutomation != null) return sqlSessionOfAutomation;
        var mySqlUrl = new PropertiesUtils().getProperty(AutoDBUtils.class, "datasource.url.automation");
        var userName = new PropertiesUtils().getProperty(AutoDBUtils.class, "datasource.username.automation");
        var passWord = new PropertiesUtils().getProperty(AutoDBUtils.class, "datasource.password.automation");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfAutomation = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), AutoDBUtils.class);
        return sqlSessionOfAutomation;
    }
}
