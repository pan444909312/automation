package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoCaseExecutionChartEntity;
import com.miller.mapper.report.AutoCaseExecutionChartMapper;
import com.miller.service.report.AutoCaseExecutionChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自动化用例执行趋势表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Service
public class AutoCaseExecutionChartServiceImpl extends ServiceImpl<AutoCaseExecutionChartMapper, AutoCaseExecutionChartEntity> implements AutoCaseExecutionChartService {

    @Autowired
    AutoCaseExecutionChartMapper autoCaseExecutionChartMapper;

    /**
     * 通过传入月份months，计算前months月至今的executionType类型的平均执行用例数
     *
     * @param months
     * @param executionType
     * @return
     */
    @Override
    public Integer getMonthsAverageExecutionCase(int months, int executionType) {
        long midnightTimestampPlusMonths = TimestampUtils.getMidnightTimestampPlusMonths(-months);
        List<AutoCaseExecutionChartEntity> autoCaseIncreaseChartEntityList =
                autoCaseExecutionChartMapper.selectList(
                        new QueryWrapper<AutoCaseExecutionChartEntity>()
                                .ge("create_time", midnightTimestampPlusMonths)
                                .eq("execution_type", executionType));
        int betweenDays = (int) TimestampUtils.getTimestampToNowBetweenDays(midnightTimestampPlusMonths);

        if (autoCaseIncreaseChartEntityList.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (AutoCaseExecutionChartEntity autoCaseExecutionChartEntity : autoCaseIncreaseChartEntityList) {
            sum = sum + autoCaseExecutionChartEntity.getExecutionCase();
        }
        return sum / betweenDays;
    }

    /**
     * 检查自动化用例执行趋势表，今日是否同步过对应执行类型的数据
     *
     * @param executionType 执行策略
     * @return 是 返回true，否 返回 false
     */
    @Override
    public boolean checkTodayHasData(int executionType, String projectId) {
        // 昨天0：00
        long yesterdayStart = TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis());
        // 今日0：00
        long yesterdayEnd = yesterdayStart + 60 * 60 * 24 * 1000;

        List<AutoCaseExecutionChartEntity> autoCaseExecutionChartEntityList = autoCaseExecutionChartMapper.selectList(new QueryWrapper<AutoCaseExecutionChartEntity>()
                .ge("create_time", yesterdayEnd)
                .eq("execution_type", executionType)
                .eq("project_id", projectId));
        return !autoCaseExecutionChartEntityList.isEmpty();
    }
}
