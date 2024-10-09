package com.miller.service.data.sql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.service.data.entity.AutoCaseRoiEntity;
import com.miller.service.data.mapper.AutoCaseRoiMapper;
import org.apache.ibatis.session.SqlSession;

public class AutoCaseRoiSql {
    private SqlSession sqlSession;
    public AutoCaseRoiSql(SqlSession sqlSession){
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
    public int saveAutoCaseRoi(AutoCaseRoiEntity autoCaseRoi){
        return  getAutoCaseRoiMapper().insert(autoCaseRoi);
    }
    public int updateAutoCaseRoi(AutoCaseRoiEntity autoCaseRoi){
        UpdateWrapper<AutoCaseRoiEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AutoCaseRoiEntity> lambda = updateWrapper.lambda();
        lambda.eq(AutoCaseRoiEntity::getScenarioId,autoCaseRoi.getScenarioId());
        lambda.set(AutoCaseRoiEntity::getTimes,autoCaseRoi.getTimes());
        lambda.set(AutoCaseRoiEntity::getSaveTime,autoCaseRoi.getSaveTime());
        lambda.set(AutoCaseRoiEntity::getRoi,autoCaseRoi.getRoi());
        lambda.set(AutoCaseRoiEntity::getExecutionUser,autoCaseRoi.getExecutionUser());
        lambda.set(AutoCaseRoiEntity::getUpdateTime,System.currentTimeMillis());
        lambda.set(AutoCaseRoiEntity::getScenarioName,autoCaseRoi.getScenarioName());
        lambda.set(AutoCaseRoiEntity::getMaintenanceTime,autoCaseRoi.getMaintenanceTime());
        lambda.set(AutoCaseRoiEntity::getDevelopmentTime,autoCaseRoi.getDevelopmentTime());
        lambda.set(AutoCaseRoiEntity::getManualTestTime,autoCaseRoi.getManualTestTime());
        return  getAutoCaseRoiMapper().update(updateWrapper);
    }
}
