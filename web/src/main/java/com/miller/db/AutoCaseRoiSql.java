package com.miller.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.entity.report.AutoCaseRoi;
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
    public AutoCaseRoi getAutoCaseRoi(String scenarioId){
        QueryWrapper<AutoCaseRoi> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<AutoCaseRoi> lambda = queryWrapper.lambda();
        lambda.eq(AutoCaseRoi::getScenarioId,scenarioId);
        return  getAutoCaseRoiMapper().selectOne(queryWrapper);
    }
    public int saveAutoCaseRoi(AutoCaseRoi autoCaseRoi){
        return  getAutoCaseRoiMapper().insert(autoCaseRoi);
    }
    public int updateAutoCaseRoi(AutoCaseRoi autoCaseRoi){
        UpdateWrapper<AutoCaseRoi> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<AutoCaseRoi> lambda = updateWrapper.lambda();
        lambda.eq(AutoCaseRoi::getScenarioId,autoCaseRoi.getScenarioId());
        lambda.set(AutoCaseRoi::getTimes,autoCaseRoi.getTimes());
        lambda.set(AutoCaseRoi::getSaveTime,autoCaseRoi.getSaveTime());
        lambda.set(AutoCaseRoi::getRoi,autoCaseRoi.getRoi());
        lambda.set(AutoCaseRoi::getExecutionUser,autoCaseRoi.getExecutionUser());
        lambda.set(AutoCaseRoi::getUpdateTime,System.currentTimeMillis());
        return  getAutoCaseRoiMapper().update(updateWrapper);
    }
}
