package com.miller.market.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panda.market.dal.dto.UserDto;
import com.panda.market.dal.entity.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 */
public interface UserMapper extends BaseMapper<User> {

    UserDto getUser(Integer userId);

}
