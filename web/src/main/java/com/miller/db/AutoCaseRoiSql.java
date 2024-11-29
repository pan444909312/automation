package com.miller.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.mapper.report.AutoCaseRoiMapper;
import org.apache.ibatis.session.SqlSession;

public class AutoCaseRoiSql {
    private SqlSession sqlSession;
    public  AutoCaseRoiSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public AutoCaseRoiMapper getAutoCaseRoiMapper(){
        return sqlSession.getMapper(AutoCaseRoiMapper.class);
    }
    public AutoCaseRoiEntity getAutoCaseRoi(String scenarioId){
        QueryWrapper<AutoCaseRoiEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoCaseRoiEntity> lambda = queryWrapper.lambda();
        lambda.eq(AutoCaseRoiEntity::getScenarioId,scenarioId);
        return  getAutoCaseRoiMapper().selectOne(queryWrapper);
    }
    public int saveAutoCaseRoi(AutoCaseRoiEntity autoCaseRoiEntity){
        return  getAutoCaseRoiMapper().insert(autoCaseRoiEntity);
    }
    public int updateAutoCaseRoi(AutoCaseRoiEntity autoCaseRoiEntity){
        UpdateWrapper<AutoCaseRoiEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AutoCaseRoiEntity> lambda = updateWrapper.lambda();
        lambda.eq(AutoCaseRoiEntity::getScenarioId, autoCaseRoiEntity.getScenarioId());
        lambda.set(AutoCaseRoiEntity::getTimes, autoCaseRoiEntity.getTimes());
        lambda.set(AutoCaseRoiEntity::getSaveTime, autoCaseRoiEntity.getSaveTime());
        lambda.set(AutoCaseRoiEntity::getRoi, autoCaseRoiEntity.getRoi());
        lambda.set(AutoCaseRoiEntity::getExecutionUser, autoCaseRoiEntity.getExecutionUser());
        lambda.set(AutoCaseRoiEntity::getUpdateTime,System.currentTimeMillis());
        return  getAutoCaseRoiMapper().update(updateWrapper);
    }
}
