package com.miller.userapp.module.data.basic.service;

import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.miller.userapp.module.data.basic.db.SysAppConfigSql;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

public class SysAppConfigService {
    private SysAppConfigSql sysAppConfigSql;
    public SysAppConfigService(SqlSession sqlSession){
        this.sysAppConfigSql = new SysAppConfigSql(sqlSession);
    }

    public String getSysAppConfigByKey(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            return null;
        }
        SysAppConfigEntity sysAppConfig = sysAppConfigSql.getSysAppConfigByKey(configKey);
        if (sysAppConfig == null) {
            return null;
        }

        return sysAppConfig.getConfigValue();
    }
}
