package com.miller.service.data.sql;

import com.miller.service.data.entity.AutoCaseRoiLogEntity;
import com.miller.service.data.mapper.AutoCaseRoiLogMapper;
import org.apache.ibatis.session.SqlSession;

public class AutoCaseRoiLogSql {
    private SqlSession sqlSession;
    public AutoCaseRoiLogSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public AutoCaseRoiLogMapper getAutoCaseRoiLogMapper(){
        return sqlSession.getMapper(AutoCaseRoiLogMapper.class);
    }
    public int saveAutoCaseRoiLog(AutoCaseRoiLogEntity autoCaseRoiLog){
        return  getAutoCaseRoiLogMapper().insert(autoCaseRoiLog);
    }
}
