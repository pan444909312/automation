package com.miller.service.framework.report.sql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.platform.User;
import com.miller.mapper.platform.UserMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @author panjuxiang
 * @since 2025/3/7 11:15
 */
public class UserSql {
    private SqlSession sqlSession;
    public UserSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserMapper getUserSqlMapper(){
        return sqlSession.getMapper(UserMapper.class);
    }

    public String getUserIdByUserEmail(String email){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",email);
        User user = getUserSqlMapper().selectOne(userQueryWrapper);
        return user.getUserId();
    }
}
