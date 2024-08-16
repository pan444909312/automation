package com.miller.userapp.module.data.user.db;

import com.hungrypanda.app.server.entity.device.DeviceLoginInfoEntity;
import com.miller.userapp.mapper.user.DeviceLoginInfoMapper;
import org.apache.ibatis.session.SqlSession;

public class DeviceLoginInfoSql {
    private SqlSession sqlSession;
    public  DeviceLoginInfoSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public DeviceLoginInfoMapper getDeviceLoginInfoMapper(){
        return sqlSession.getMapper(DeviceLoginInfoMapper.class);
    }
    public void insert(DeviceLoginInfoEntity deviceLoginInfo){
        getDeviceLoginInfoMapper().insert(deviceLoginInfo);
    }
}
