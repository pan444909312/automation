package com.miller.userapp.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.user.UserRegInfoEntity;
import com.hungrypanda.app.server.entity.user.UserShopOrderNumEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserShopOrderNumMapper extends BaseMapper<UserShopOrderNumEntity> {
}
