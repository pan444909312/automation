package com.miller.service.framework.report.sql;

import com.miller.service.framework.report.entity.AutoCaseRoiLogEntity;
import com.miller.service.framework.report.mapper.AutoCaseRoiLogMapper;
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
