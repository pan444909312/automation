package com.miller.service.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApifoxDBUtils {

    private static SqlSession sqlSessionOfApifox;


    /**
     * 获取数据库链接 “apifox" 库
     *
     * @return SqlSession
     */
    @Bean
    public synchronized ApifoxDBUtils getDBOfApifox() {
        if (sqlSessionOfApifox != null) return this;
        var mySqlUrl = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.url.apifox");
        var userName = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.username.apifox");
        var passWord = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.password.apifox");
        log.info("mySqlUrl:".concat(mySqlUrl));
        log.info("userName:".concat(userName));
        log.info("passWord:".concat(passWord));

        var myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSessionOfApifox = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), com.miller.service.util.ApifoxDBUtils.class);
        return this;
    }

    public ApifoxDBUtils openSession(){
        return this.getDBOfApifox();
    }

    public <T> T getMapper(Class<T> type) {
        return sqlSessionOfApifox.getMapper(type);
    }


}
