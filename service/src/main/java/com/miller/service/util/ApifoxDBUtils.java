package com.miller.service.util;

import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

@Slf4j
public class ApifoxDBUtils {

    private static SqlSession sqlSessionOfApifox;


    /**
     * 获取数据库链接 “apifox" 库
     *
     * @return SqlSession
     */
    public static synchronized ApifoxDBUtils openSession() {
        var mySqlUrl = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.url.apifox");
        var userName = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.username.apifox");
        var passWord = new PropertiesUtils().getProperty(com.miller.service.util.ApifoxDBUtils.class, "datasource.password.apifox");
        log.info("mySqlUrl:".concat(mySqlUrl));
        log.info("userName:".concat(userName));
        log.info("passWord:".concat(passWord));

        var myBatisPlusConfig = new MyBatisPlusConfig();
        try {
            if (sqlSessionOfApifox != null) {
                if (!sqlSessionOfApifox.getConnection().isValid(5)) {
                    return new ApifoxDBUtils();
                } else {
                    sqlSessionOfApifox.close();
                }
            }
            sqlSessionOfApifox = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), com.miller.service.util.ApifoxDBUtils.class);
        } catch (SQLException e) {
            log.error("DB apifox 连接空闲超时自动关闭：触发重新连接");
            sqlSessionOfApifox = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource(), com.miller.service.util.ApifoxDBUtils.class);
        }

        return new ApifoxDBUtils();
    }



    public <T> T getMapper(Class<T> type) {
        return sqlSessionOfApifox.getMapper(type);
    }


}
