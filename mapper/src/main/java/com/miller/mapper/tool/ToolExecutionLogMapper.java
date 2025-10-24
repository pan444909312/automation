package com.miller.mapper.tool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import com.miller.entity.tools.ToolExecutionLogEntity;
import com.miller.entity.tools.ToolExecutionStatisticsResultEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ToolExecutionLogMapper extends BaseMapper<ToolExecutionLogEntity> {

    List<ToolExecutionLogEntity> getToolExecutionLogByToolId(Long id);

    List<ToolExecutionStatisticsResultEntity>  getToolEfficiencyStatsByToolId(Long id);

}
