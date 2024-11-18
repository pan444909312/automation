package com.miller.userapp.module.data.member.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.miller.userapp.mapper.member.MemberCityMapper;
import org.apache.ibatis.session.SqlSession;

public class MemberCitySql {
    private SqlSession sqlSession;

    public MemberCitySql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public MemberCityMapper getMemberCityMapper(){
        return sqlSession.getMapper(MemberCityMapper.class);
    }
    public MemberCityEntity getMemberCityEntity(Long cityId,Long memberCityId){
        QueryWrapper<MemberCityEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberCityEntity> lambda = queryWrapper.lambda();
        lambda.eq(MemberCityEntity::getCityId,cityId);
        lambda.eq(MemberCityEntity::getMemberCityId,memberCityId);
        lambda.eq(MemberCityEntity::getIsDel,0);
        lambda.eq(MemberCityEntity::getOnlineStatus,1);
        return  getMemberCityMapper().selectOne(queryWrapper);

    }
}
