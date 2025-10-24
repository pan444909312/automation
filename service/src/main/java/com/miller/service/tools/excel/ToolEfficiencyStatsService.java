package com.miller.service.tools.excel;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.tools.ToolEfficiencyStatsEntity;

import java.util.List;

public interface ToolEfficiencyStatsService extends IService<ToolEfficiencyStatsEntity> {

    boolean toolEfficiencyStatsSavaOrUpdate(ToolEfficiencyStatsEntity entity, String executor);

    List<ToolEfficiencyStatsEntity> getAllToolEfficiencyStats();
}
