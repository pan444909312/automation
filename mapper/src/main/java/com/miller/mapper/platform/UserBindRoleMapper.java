package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.UserBindRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 * 用户绑定角色 Mapper 接口
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Mapper
public interface UserBindRoleMapper extends BaseMapper<UserBindRole> {
    Set<String> getRolesByUserName(String username);
}
