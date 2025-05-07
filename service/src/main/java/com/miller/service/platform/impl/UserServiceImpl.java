package com.miller.service.platform.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.platform.Permission;
import com.miller.entity.platform.User;
import com.miller.mapper.platform.PermissionMapper;
import com.miller.mapper.platform.UserMapper;
import com.miller.service.platform.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 通过邮箱来查找一个用户
     *
     * @param email 邮箱
     * @return User
     */
    @Override
    public User getUserByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        // 排除掉password列的值
//        queryWrapper.select(User.class, info -> !info.getColumn().equals("password"));
        // 查询条件
        queryWrapper.eq("email", email);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", name);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User getUserById(String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 根据用户名获取用户拥有的权限菜单列表
     */
    @Override
    public List<Map<String, Object>> getMenuList(String username) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 获取父权限
        List<Permission> parentPermissionList = permissionMapper.getParentPermissionByUsername(username);
        for (Permission parentPermission : parentPermissionList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("menu", parentPermission);
            // 根据父权限ID，查找出此父ID下的所有子节点数据
            List<Permission> childrenPermissionList = permissionMapper.getChildrenPermissionByParentId(parentPermission.getPermissionId());
            map.put("subMenu", childrenPermissionList);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<String> getProjectListByUserIdOrEmail(String userId) {
        List<String> projectListByUserId = userMapper.getProjectListByUserIdOrEmail(userId);
        return projectListByUserId;
    }

    @Override
    public List<User> getUserList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 0);
        List<User> list = userMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * todo
     * 根据token解析返回userId
     * @param token
     * @return
     */
    @Override
    public String getUserIdByToken(String token) {
        return "";
    }
}
