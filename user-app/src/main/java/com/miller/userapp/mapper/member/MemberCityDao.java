package com.miller.userapp.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCityDao extends BaseMapper<MemberCityEntity> {
    MemberCityEntity getMemberCityByCityId(Long cityId);
}
