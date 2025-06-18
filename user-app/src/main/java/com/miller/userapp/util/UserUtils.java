package com.miller.userapp.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.miller.userapp.mapper.user.UserMapper;
import org.apache.ibatis.session.SqlSession;

public class UserUtils {



    private static SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
    private static UserMapper userMapper = sqlSession.getMapper(UserMapper.class);


    private UserUtils() {
    }



    /**
     * 检查指定用户ID是否为新用户
     *
     * @param userId 用户ID，用于查询用户信息
     * @return boolean 返回是否为新增用户：
     * true - 用户不存在或状态为新用户(状态值为0)；
     * false - 用户存在且状态为非新用户
     */
    public static boolean isNewUser(String userId) {
        // 根据用户ID查询用户信息
        UserEntity user = userMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userId));

        // 用户不存在则判定为新用户
        if (user == null) {
            return true;
        }

        // 根据用户状态判断是否为新用户(状态0表示新用户)
        return user.getNewUserStatus() == 0;
    }

    /**
     * 更新用户的新用户状态
     *
     * @param userId 用户ID，用于唯一标识要更新的用户
     * @param status 要设置的新用户状态值
     *               (0表示非新用户，1表示新用户)
     */
    public static void updateUserNewUserStatus(String userId, int status) {
        // 根据用户ID查询用户实体
        UserEntity user = userMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userId));

        // 更新用户的新用户状态
        user.setNewUserStatus(status);

        userMapper.updateById(user);
    }


}
