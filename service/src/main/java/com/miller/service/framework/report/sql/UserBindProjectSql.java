package com.miller.service.framework.report.sql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.platform.UserBindProject;
import com.miller.mapper.platform.UserBindProjectMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * @author panjuxiang
 * @since 2025/3/7 11:07
 */
public class UserBindProjectSql {

    private SqlSession sqlSession;
    public UserBindProjectSql(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    public UserBindProjectMapper getUserBindProjectMapper(){
        return sqlSession.getMapper(UserBindProjectMapper.class);
    }

    public String getProjectIdByUserId(String userId){
        QueryWrapper<UserBindProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        UserBindProject userBindProject = getUserBindProjectMapper().selectOne(queryWrapper);
        return userBindProject.getProjectId();
    }
}
