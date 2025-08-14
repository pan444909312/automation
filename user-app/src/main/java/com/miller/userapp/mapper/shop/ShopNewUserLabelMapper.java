package com.miller.userapp.mapper.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * hp_user_label表，用于首页商卡新人首单标签测试
 */
@Mapper
public interface ShopNewUserLabelMapper extends BaseMapper<UserLabelEntity> {

}
