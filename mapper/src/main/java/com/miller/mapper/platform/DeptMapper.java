package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.Dept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    Dept selectByName(String name);

}
