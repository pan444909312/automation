package com.miller.service.data.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.entity.report.AutoExecutionRecordEntity;
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
    public AutoExecutionRecordEntity getAutoExecutionRecordBySuite(String scenarioId){ //suite可能更新多次，查找已经更新过的
        QueryWrapper<AutoExecutionRecordEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecordEntity> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecordEntity::getScenarioId,scenarioId);
        lambda.ne(AutoExecutionRecordEntity::getExecutionStatus,-1);
        lambda.orderByDesc(AutoExecutionRecordEntity::getUpdateTime);
        lambda.last("limit 1");
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }
    public int updateAutoExecutionRecord(Long id ,int result){
        UpdateWrapper<AutoExecutionRecordEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AutoExecutionRecordEntity> lambda = updateWrapper.lambda();
        lambda.eq(AutoExecutionRecordEntity::getId,id);
        lambda.set(AutoExecutionRecordEntity::getExecutionStatus,result);
        lambda.set(AutoExecutionRecordEntity::getUpdateTime,System.currentTimeMillis());
        return  getAutoExecutionRecordMapper().update(updateWrapper);
    }
}
