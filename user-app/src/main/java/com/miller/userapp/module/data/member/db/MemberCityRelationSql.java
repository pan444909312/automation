package com.miller.userapp.module.data.member.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.member.MemberCityRelationEntity;
import com.miller.userapp.mapper.member.MemberCityRelationMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MemberCityRelationSql {
    private SqlSession sqlSession;

    public MemberCityRelationSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;

    }
    public MemberCityRelationMapper getMemberCityRelationMapper(){
        return sqlSession.getMapper(MemberCityRelationMapper.class);
    }
    public List<MemberCityRelationEntity> getMemberCityEntity(String city){
        QueryWrapper<MemberCityRelationEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MemberCityRelationEntity> lambda = queryWrapper.lambda();
        lambda.eq(MemberCityRelationEntity::getCityName,city);
        lambda.eq(MemberCityRelationEntity::getIsDelete,0);
        return  getMemberCityRelationMapper().selectList(queryWrapper);

    }
}
