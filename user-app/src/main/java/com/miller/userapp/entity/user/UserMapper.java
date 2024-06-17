package com.miller.userapp.entity.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hungrypanda.app.server.entity.user.UserEntity;

/**
 * 添加对数据库 user 表的操作
 * <p>
 * 查询数据库表与对象的映射关系方法：代码中搜索 @TableName("表名")，例如: @TableName("user")
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/17 15:34:40
 */
public interface UserMapper extends BaseMapper<UserEntity> {
}
