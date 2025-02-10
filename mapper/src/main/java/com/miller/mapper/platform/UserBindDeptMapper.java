package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.UserBindDept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBindDeptMapper extends BaseMapper<UserBindDept> {

    UserBindDept selectByUserId(String userId);

}
