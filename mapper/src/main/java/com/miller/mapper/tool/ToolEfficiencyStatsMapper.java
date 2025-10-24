package com.miller.mapper.tool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ToolEfficiencyStatsMapper extends BaseMapper<ToolEfficiencyStatsEntity> {

    public List<ToolEfficiencyStatsEntity> getAll();

}
