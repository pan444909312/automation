package com.miller.userapp.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.account.UserAccountEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity> {
}
