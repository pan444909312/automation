package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.Permission;
import com.miller.mapper.platform.PermissionMapper;
import com.miller.service.platform.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 21:03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据用户名查出用户拥有的所有权限
     *
     * @param username 用户名
     * @return Set<String>
     */
    @Override
    public Set<String> getAllPermissionByUsername(String username) {
        return permissionMapper.getAllPermissionCodeByUsername(username);
    }
}
