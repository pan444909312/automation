package com.miller.userapp.module.data.user.db;

import com.hungrypanda.app.server.entity.user.UserRegInfoEntity;
import com.miller.userapp.mapper.user.UserRegInfoMapper;
import org.apache.ibatis.session.SqlSession;

public class UserRegInfoSql {
    private SqlSession sqlSession;
    public  UserRegInfoSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserRegInfoMapper getUserLogMapper(){
        return sqlSession.getMapper(UserRegInfoMapper.class);
    }
    public void insert(UserRegInfoEntity userRegInfo){
        getUserLogMapper().insert(userRegInfo);
    }
}
