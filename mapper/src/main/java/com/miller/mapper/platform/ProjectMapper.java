package com.miller.mapper.platform;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.platform.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author Miller
 * @since 2024/12/24 20:13
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    Project findByName(String name);
}
