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

    /**
     * 根据场景ID获取尚未完成的最新一条执行记录（executionStatus = -1）
     * @param scenarioId 场景ID
     * @return 符合条件的最新记录或null（如果没有找到）
     */
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
    /**
     * 根据场景ID获取已更新过的最新一条执行记录（executionStatus != -1）
     * 适用于suite可能被多次更新的情况
     * @param scenarioId 场景ID
     * @return 符合条件的最新记录或null（如果没有找到）
     */
    public AutoExecutionRecordEntity getAutoExecutionRecordBySuite(String scenarioId){
        QueryWrapper<AutoExecutionRecordEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoExecutionRecordEntity> lambda = queryWrapper.lambda();
        lambda.eq(AutoExecutionRecordEntity::getScenarioId,scenarioId);
        lambda.ne(AutoExecutionRecordEntity::getExecutionStatus,-1);
        lambda.orderByDesc(AutoExecutionRecordEntity::getUpdateTime);
        lambda.last("limit 1");
        return  getAutoExecutionRecordMapper().selectOne(queryWrapper);
    }

    /**
     * 更新指定自动执行记录的状态及更新时间
     * @param autoExecutionRecordEntity 需要更新的记录实体
     * @param value 新的执行状态枚举值
     * @return 更新成功的记录数（通常为1）
     */
    public int updateAutoExecutionRecord(AutoExecutionRecordEntity autoExecutionRecordEntity , ExecutionStatusEnum value){
        LambdaUpdateWrapper<AutoExecutionRecordEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AutoExecutionRecordEntity::getId,autoExecutionRecordEntity.getId());
        updateWrapper.set(AutoExecutionRecordEntity::getExecutionStatus,value.getCode());
        updateWrapper.set(AutoExecutionRecordEntity::getUpdateTime,System.currentTimeMillis());
        return  getAutoExecutionRecordMapper().update(autoExecutionRecordEntity, updateWrapper);
    }
}
