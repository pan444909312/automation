package com.miller.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.platform.UserBindRole;

import java.util.Set;

/**
 * <p>
 * 用户绑定角色 服务类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
public interface UserBindRoleService extends IService<UserBindRole> {
    Set<String> getRolesByUserName(String username);
}
