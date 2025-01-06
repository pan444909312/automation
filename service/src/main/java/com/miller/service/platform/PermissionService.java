package com.miller.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.platform.Permission;

import java.util.Set;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
public interface PermissionService extends IService<Permission> {

    Set<String> getAllPermissionByUsername(String username);
}
