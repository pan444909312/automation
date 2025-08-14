package com.miller.userapp.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.dto.member.MemberInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemberDao extends BaseMapper<MemberInfo> {
    MemberInfo getMemberByUserIdAndCityName( Long userId,String cityName,  long nowTime);
}
