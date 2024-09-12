package com.miller.userapp.module.data.promotion.redpacket.db;

import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import org.apache.ibatis.session.SqlSession;

public class UserCdkeySql {
    private SqlSession sqlSession;

    public UserCdkeySql(SqlSession sqlSession) {
        this.sqlSession=sqlSession;
    }

    public UserCdKeyMapper getUserCdkeyMapper(){
        return sqlSession.getMapper(UserCdKeyMapper.class);
    }
    public void insert(UserCdKeyEntity userCdKey ){
        getUserCdkeyMapper().insert(userCdKey);
    }
}
