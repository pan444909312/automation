package com.miller.service.framework.report.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.mapper.report.AutoExecutionRecordMapper;
import org.apache.ibatis.session.SqlSession;

public class AutoExecutionRecordSql {
    private SqlSession sqlSession;
    public AutoExecutionRecordSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public AutoExecutionRecordMapper getAutoExecutionRecordMapper(){
        return sqlSession.getMapper(AutoExecutionRecordMapper.class);
    }
    public int saveAutoExecutionRecord(AutoExecutionRecordEntity autoExecutionRecord){
        return  getAutoExecutionRecordMapper().insert(autoExecutionRecord);
    }
    public AutoExecutionRecordEntity getAutoExecutionRecord(String scenarioId){
        QueryWrapper<AutoExecutionRecordEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecordEntity> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecordEntity::getScenarioId,scenarioId);
        lambda.eq(AutoExecutionRecordEntity::getExecutionStatus,-1);
        lambda.orderByDesc(AutoExecutionRecordEntity::getId);
        lambda.last("limit 1");
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }
    //suite可能更新多次，查找已经更新过的
    public AutoExecutionRecordEntity getAutoExecutionRecordBySuite(String scenarioId){
        QueryWrapper<AutoExecutionRecordEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecordEntity> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecordEntity::getScenarioId,scenarioId);
        lambda.ne(AutoExecutionRecordEntity::getExecutionStatus,-1);
        lambda.orderByDesc(AutoExecutionRecordEntity::getUpdateTime);
        lambda.last("limit 1");
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }
    public int updateAutoExecutionRecord(AutoExecutionRecordEntity autoExecutionRecordEntity , ExecutionStatusEnum value){
        LambdaUpdateWrapper<AutoExecutionRecordEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AutoExecutionRecordEntity::getId,autoExecutionRecordEntity.getId());
        updateWrapper.set(AutoExecutionRecordEntity::getExecutionStatus,value.getCode());
        updateWrapper.set(AutoExecutionRecordEntity::getUpdateTime,System.currentTimeMillis());
        return  getAutoExecutionRecordMapper().update(autoExecutionRecordEntity, updateWrapper);
    }
}
