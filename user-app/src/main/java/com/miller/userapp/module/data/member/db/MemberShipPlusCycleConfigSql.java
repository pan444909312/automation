package com.miller.userapp.module.data.member.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MembershipPlusCycleConfigurationEntity;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberShipPlusCycleConfigMapper;
import org.apache.ibatis.session.SqlSession;

public class MemberShipPlusCycleConfigSql {
    private SqlSession sqlSession;

    public MemberShipPlusCycleConfigSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public MemberShipPlusCycleConfigMapper getMemberShipPlusCycleConfigSqlMapper(){
        return sqlSession.getMapper(MemberShipPlusCycleConfigMapper.class);
    }
    public MembershipPlusCycleConfigurationEntity getMembershipPlusCycleConfigurationEntity(Long memberCityId){
        QueryWrapper<MembershipPlusCycleConfigurationEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MembershipPlusCycleConfigurationEntity> lambda = queryWrapper.lambda();
        lambda.eq(MembershipPlusCycleConfigurationEntity::getMemberCityId,memberCityId);
        lambda.eq(MembershipPlusCycleConfigurationEntity::getIsDel,0);
        lambda.eq(MembershipPlusCycleConfigurationEntity::getStartUsing,1);
        return  getMemberShipPlusCycleConfigSqlMapper().selectOne(queryWrapper);
    }
    public Integer updateMembershipPlusCycleConfigurationEntity(Long memberCityId,String dayLimit){
        UpdateWrapper<MembershipPlusCycleConfigurationEntity> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<MembershipPlusCycleConfigurationEntity> lambda = updateWrapper.lambda();
        lambda.eq(MembershipPlusCycleConfigurationEntity::getMemberCityId,memberCityId);
        lambda.eq(MembershipPlusCycleConfigurationEntity::getIsDel,0);
        lambda.eq(MembershipPlusCycleConfigurationEntity::getStartUsing,1);
        lambda.set(MembershipPlusCycleConfigurationEntity::getDayLimit,dayLimit);
        return  getMemberShipPlusCycleConfigSqlMapper().update(null,updateWrapper);
    }
    public int insert(MembershipPlusCycleConfigurationEntity membershipPlusCycleConfigurationEntity){
        return  getMemberShipPlusCycleConfigSqlMapper().insert(membershipPlusCycleConfigurationEntity);
    }
}
