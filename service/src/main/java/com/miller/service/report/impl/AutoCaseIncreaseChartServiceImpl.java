package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoCaseIncreaseChartEntity;
import com.miller.mapper.report.AutoCaseIncreaseChartMapper;
import com.miller.service.report.AutoCaseIncreaseChartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.common.util.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自动化用例增长趋势表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Service
public class AutoCaseIncreaseChartServiceImpl extends ServiceImpl<AutoCaseIncreaseChartMapper, AutoCaseIncreaseChartEntity> implements AutoCaseIncreaseChartService {

    @Autowired
    AutoCaseIncreaseChartMapper autoCaseIncreaseChartMapper;

    /**
     * 通过传入月份months，计算前months月至今的平均新增用例数
     *
     * @param months
     * @return
     */
    @Override
    public Integer getMonthsAverageIncreaseCase(int months) {
        long midnightTimestampPlusMonths = TimestampUtils.getMidnightTimestampPlusMonths(-months);
        List<AutoCaseIncreaseChartEntity> autoCaseIncreaseChartEntityList =
                autoCaseIncreaseChartMapper.selectList(
                        new QueryWrapper<AutoCaseIncreaseChartEntity>()
                                .ge("create_time", midnightTimestampPlusMonths));
        int betweenDays = (int) TimestampUtils.getTimestampToNowBetweenDays(midnightTimestampPlusMonths);
        if (autoCaseIncreaseChartEntityList.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (AutoCaseIncreaseChartEntity autoCaseIncreaseChartEntity : autoCaseIncreaseChartEntityList) {
            sum = sum + autoCaseIncreaseChartEntity.getIncreaseCase();
        }
        return sum / betweenDays;
    }

    /**
     * 检查自动化用例增长趋势表，今日是否同步过数据
     * @return 是 返回true，否 返回 false
     */
    @Override
    public boolean checkTodayHasData() {
        // 昨天0：00
        long yesterdayStart = TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis());
        // 今日0：00
        long yesterdayEnd = yesterdayStart + 60 * 60 * 24 * 1000;

        List<AutoCaseIncreaseChartEntity> autoCaseIncreaseChartEntityList = autoCaseIncreaseChartMapper.selectList(new QueryWrapper<AutoCaseIncreaseChartEntity>()
                .ge("create_time", yesterdayEnd));
        return !autoCaseIncreaseChartEntityList.isEmpty();
    }
}
