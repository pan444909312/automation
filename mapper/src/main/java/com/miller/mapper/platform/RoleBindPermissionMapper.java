package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.RoleBindPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色绑定的权限 Mapper 接口
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
@Mapper
public interface RoleBindPermissionMapper extends BaseMapper<RoleBindPermission> {

}
