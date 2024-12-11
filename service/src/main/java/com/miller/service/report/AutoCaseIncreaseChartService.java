package com.miller.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.AutoCaseIncreaseChartEntity;

/**
 * <p>
 * 自动化用例增长趋势表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
public interface AutoCaseIncreaseChartService extends IService<AutoCaseIncreaseChartEntity> {

    /**
     * 通过传入月份months，计算前months月至今的平均新增用例数
     * @param months
     * @return
     */
    Integer getMonthsAverageIncreaseCase(int months);

    /**
     * 检查自动化用例增长趋势表，今日是否同步过数据
     * @return 是 返回true，否 返回 false
     */
    boolean checkTodayHasData();

}
