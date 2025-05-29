package com.miller.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.platform.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
public interface UserService extends IService<User> {
    User getUserByEmail(String email);

    User getUserByName(String name);

    User getUserById(String id);

    List<Map<String, Object>> getMenuList(String username);

    List<String> getProjectListByUserIdOrEmail(String userId);

    List<User> getUserList();
}
