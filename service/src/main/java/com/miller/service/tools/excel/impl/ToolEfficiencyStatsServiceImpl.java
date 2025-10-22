package com.miller.service.tools.excel.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import com.miller.entity.tools.ToolExecutionLogEntity;
import com.miller.entity.tools.ToolExecutionStatisticsResultEntity;
import com.miller.mapper.tool.ToolEfficiencyStatsMapper;
import com.miller.mapper.tool.ToolExecutionLogMapper;
import com.miller.service.apifox.enums.ToolEnum;
import com.miller.service.tools.excel.ToolEfficiencyStatsService;
import com.miller.service.tools.excel.ToolExecutionLogService;
import com.miller.service.util.DateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Service
public class ToolEfficiencyStatsServiceImpl extends ServiceImpl<ToolEfficiencyStatsMapper, ToolEfficiencyStatsEntity>
        implements ToolEfficiencyStatsService {

    @Autowired
    private ToolExecutionLogService logService;

    @Autowired
    private ToolExecutionLogMapper toolExecutionLogMapper;



    public boolean toolEfficiencyStatsSavaOrUpdate(ToolEfficiencyStatsEntity entity, String executor) {

        if (ObjectUtils.isEmpty(executor)) {
            throw new RuntimeException("请求头必须携带：executor ，且不能为空");
        }

        if (ObjectUtils.isEmpty(entity) || ObjectUtils.isEmpty(entity.getToolCode())) {
            throw new RuntimeException("请求参数异常，toolCode 不能为空");
        }

        DateUtils.checkDateFormat(entity.getImplementationDate());


        QueryWrapper<ToolEfficiencyStatsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tool_code", entity.getToolCode());
        ToolEfficiencyStatsEntity toolEfficiencyStatsEntity = this.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(toolEfficiencyStatsEntity)) {
            toolEfficiencyStatsEntity = new ToolEfficiencyStatsEntity();

            BeanUtils.copyProperties(entity, toolEfficiencyStatsEntity);
            toolEfficiencyStatsEntity.setStatus(ToolEnum.VALID.getStatus());
            toolEfficiencyStatsEntity.setExecutionCount(1);

        } else {
            entity.setId(toolEfficiencyStatsEntity.getId())
                    .setExecutionCount(toolEfficiencyStatsEntity.getExecutionCount() + 1)
                    .setUpdatedAt(new Date(System.currentTimeMillis()));
            BeanUtils.copyProperties(entity, toolEfficiencyStatsEntity);

        }
        BigDecimal roi = BigDecimal.valueOf((double) toolEfficiencyStatsEntity.getTimeSavedPerExecution()
                * toolEfficiencyStatsEntity.getExecutionCount()
                / toolEfficiencyStatsEntity.getDevelopmentDuration());
        toolEfficiencyStatsEntity.setRoi(roi);

        this.saveOrUpdate(toolEfficiencyStatsEntity);


        // 执行记录
        if (ObjectUtils.isEmpty(toolEfficiencyStatsEntity.getId())) {
            toolEfficiencyStatsEntity = this.getOne(queryWrapper);
        }
        ToolExecutionLogEntity logEntity = new ToolExecutionLogEntity();

        logEntity.setToolId(toolEfficiencyStatsEntity.getId())
                .setExecutionStatus("success")
                .setTimeSavedPerExecution(toolEfficiencyStatsEntity.getTimeSavedPerExecution())
                .setExecutor(executor);
        logService.saveOrUpdate(logEntity);
        return true;


    }

    @Override
    public List<ToolEfficiencyStatsEntity> getAllToolEfficiencyStats() {
        List<ToolEfficiencyStatsEntity> mapperAll = this.baseMapper.getAll();
        mapperAll.forEach(item -> {
            List<ToolExecutionStatisticsResultEntity> resultEntityList = toolExecutionLogMapper.getToolEfficiencyStatsByToolId(item.getId());
            item.setExecutionStatisticsResultList(resultEntityList);
        });


        return mapperAll;
    }


}
