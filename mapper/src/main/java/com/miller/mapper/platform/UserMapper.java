package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户ID或邮箱查询用户绑定的项目下的所有缺陷
     *
     * @param userId
     * @return
     */
    List<String> getProjectListByUserIdOrEmail(String userId);
}
