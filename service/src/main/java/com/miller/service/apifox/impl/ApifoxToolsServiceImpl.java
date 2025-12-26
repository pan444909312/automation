package com.miller.service.apifox.impl;

import com.miller.entity.apifox.DTO.ApiFoxToolsExecDTO;
import com.miller.entity.tools.ToolEfficiencyStatsEntity;
import com.miller.service.apifox.ApiFoxConfigService;
import com.miller.service.apifox.ApiFoxRunReportService;
import com.miller.service.apifox.ApifoxToolsService;

import com.miller.service.apifox.enums.AttributionGroupEnum;
import com.miller.service.enums.ApiFoxEnvEnum;
import com.miller.service.tools.excel.ToolEfficiencyStatsService;
import com.miller.service.util.JavaShellUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ApifoxToolsServiceImpl implements ApifoxToolsService {


    @Autowired
    private ApiFoxConfigService apiFoxConfigService;

    @Autowired
    private ApiFoxRunReportService runReportService;

    @Autowired
    ToolEfficiencyStatsService toolEfficiencyStatsService;


    /**
     * 异步执行
     */
    @Async
    @Override
    public void scheduledTaskAsync(AttributionGroupEnum attributionGroupEnum) {
        this.execApifoxCli(attributionGroupEnum);
    }

    @Override
    public void execApifoxCli(AttributionGroupEnum attributionGroupEnum) {

        // 获取当前时间并格式化输出
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String format = sdf.format(new Date());

        // 根据小组获取对应的执行命令配置
        String groupConfig = apiFoxConfigService.getGroupConfig(attributionGroupEnum);
        groupConfig = groupConfig.replace("{{date}}", format);

        if (ObjectUtils.isEmpty(groupConfig)) {
            throw new RuntimeException("无法查询到 ApiFox config 配置信息：".concat(attributionGroupEnum.toString()));
        }

        int executeShell = JavaShellUtil.executeShell(groupConfig);

        // 避免执行的时候，响应报告文件还没创建，导致报错。
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.runReportService.parsingReport(attributionGroupEnum);
    }

    @Async
    @Override
    public void execApifoxCli(String taskId) {
        // 根据小组获取对应的执行命令配置
        String groupConfig = apiFoxConfigService.getGroupConfig(AttributionGroupEnum.DEBUG);
        groupConfig = groupConfig.replace("{{taskId}}", taskId);

        final String belongingGroup = runReportService.queryBelongingGroup(Long.valueOf(taskId));
        if (belongingGroup.equals("D")) {
            groupConfig = groupConfig.replace("{{envId}}", ApiFoxEnvEnum.D_ENV.getEnvId());
        } else {
            groupConfig = groupConfig.replace("{{envId}}", ApiFoxEnvEnum.TEST_ENV.getEnvId());
        }

        JavaShellUtil.executeShell(groupConfig);
    }

    @Override
    @Async
    public void execApifoxCli(ApiFoxToolsExecDTO execDTO) {
        // 根据小组获取对应的执行命令配置
        String groupConfig = apiFoxConfigService.getGroupConfig(AttributionGroupEnum.TOOLS);
        groupConfig = groupConfig.replace("{{taskId}}", execDTO.getTaskId())
                .replace("{{envId}}", execDTO.getEnvId())
                .replace("{{envVarList}}", execDTO.toEnvVarListStr());
        ;

        JavaShellUtil.executeShell(groupConfig);

        // 工具基础信息落库/更新
        ToolEfficiencyStatsEntity toolEfficiencyStats = execDTO.getToolEfficiencyStats();
        if (!ObjectUtils.isEmpty(toolEfficiencyStats)) {
            toolEfficiencyStatsService.toolEfficiencyStatsSavaOrUpdate(toolEfficiencyStats, execDTO.getExecutor());
        }
    }


}
