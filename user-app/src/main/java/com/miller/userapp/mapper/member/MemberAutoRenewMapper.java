package com.miller.userapp.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.member.MemberAutoRenewEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberAutoRenewMapper extends BaseMapper<MemberAutoRenewEntity> {
}
