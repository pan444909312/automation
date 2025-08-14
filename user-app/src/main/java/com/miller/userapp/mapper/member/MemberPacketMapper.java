package com.miller.userapp.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 */
@Mapper
public interface MemberPacketMapper extends BaseMapper<MemberPacketEntity> {
}
