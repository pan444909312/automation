package com.miller.service.data.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.entity.AutoExecutionRecord;
import com.miller.service.data.entity.AutoCaseRoiEntity;

import com.miller.service.data.mapper.AutoExecutionRecordMapper;
import org.apache.ibatis.session.SqlSession;

public class AutoExecutionRecordSql {
    private SqlSession sqlSession;
    public AutoExecutionRecordSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public AutoExecutionRecordMapper getAutoExecutionRecordMapper(){
        return sqlSession.getMapper(AutoExecutionRecordMapper.class);
    }
    public int saveAutoExecutionRecord(AutoExecutionRecord autoExecutionRecord){
        return  getAutoExecutionRecordMapper().insert(autoExecutionRecord);
    }
    public AutoExecutionRecord getAutoExecutionRecord(String scenarioId){
        QueryWrapper<AutoExecutionRecord> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecord> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecord::getScenarioId,scenarioId);
        lambda.eq(AutoExecutionRecord::getExecutionStatus,-1);
        lambda.last("limit 1");
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }
    public AutoExecutionRecord getAutoExecutionRecordByOne(String scenarioId){
        QueryWrapper<AutoExecutionRecord> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecord> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecord::getScenarioId,scenarioId);
        lambda.ne(AutoExecutionRecord::getExecutionStatus,-1);
        lambda.orderByAsc(AutoExecutionRecord::getUpdateTime);
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }
    public int updateAutoExecutionRecord(Long id ,int result){
        UpdateWrapper<AutoExecutionRecord> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AutoExecutionRecord> lambda = updateWrapper.lambda();
        lambda.eq(AutoExecutionRecord::getId,id);
        lambda.set(AutoExecutionRecord::getExecutionStatus,result);
        lambda.set(AutoExecutionRecord::getUpdateTime,System.currentTimeMillis());
        return  getAutoExecutionRecordMapper().update(updateWrapper);
    }
}
