package com.miller.userapp.module.data.promotion.redpacket.db;

import com.miller.userapp.mapper.redpacket.UserCdKeyCollectSourceMapper;
import org.apache.ibatis.session.SqlSession;

public class UserCdkeyCollectSourceSql {
    private SqlSession sqlSession;
    public  UserCdkeyCollectSourceSql(SqlSession sqlSession){
        this.sqlSession=sqlSession;
    }

    public UserCdKeyCollectSourceMapper getUserCdkeyCollectSourceMapper(){
        return  sqlSession.getMapper(UserCdKeyCollectSourceMapper.class);
    }
}
