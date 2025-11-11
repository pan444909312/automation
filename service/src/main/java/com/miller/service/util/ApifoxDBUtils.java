package com.miller.service.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApifoxDBUtils {

    private static SqlSession sqlSessionOfApifox;


    /**
     * 获取数据库链接 “panda_test" 库
     *
     * @return SqlSession
     */
    @Bean
    public synchronized SqlSession getDBOfApifox() {
        if (sqlSessionOfApifox != null) return sqlSessionOfApifox;
        var mySqlUrl = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.url.apifox");
        var userName = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.username.apifox");
        var passWord = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.password.apifox");
        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfApifox = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), com.miller.service.util.ApifoxDBUtils.class);
        return sqlSessionOfApifox;
    }

    public <T> T getMapper(Class<T> type) {
        return sqlSessionOfApifox.getMapper(type);
    }


}
