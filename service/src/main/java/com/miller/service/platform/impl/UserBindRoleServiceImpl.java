package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.UserBindRole;
import com.miller.mapper.platform.UserBindRoleMapper;
import com.miller.service.platform.UserBindRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 用户绑定角色 服务实现类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Service
public class UserBindRoleServiceImpl extends ServiceImpl<UserBindRoleMapper, UserBindRole> implements UserBindRoleService {

    @Autowired
    private UserBindRoleMapper userBindRoleMapper;

    @Override
    public Set<String> getRolesByUserName(String username) {
        Set<String> rolesByUserName = userBindRoleMapper.getRolesByUserName(username);
        return rolesByUserName;
    }
}
