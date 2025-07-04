package com.miller.service.report;

import com.miller.entity.report.AutoCaseExecutionChartEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自动化用例执行趋势表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
public interface AutoCaseExecutionChartService extends IService<AutoCaseExecutionChartEntity> {

    /**
     *
     * 通过传入月份months，计算前months月至今的executionType类型的平均执行用例数
     * @param months
     * @param executionType
     * @return
     */
    Integer getMonthsAverageExecutionCase(int months,int executionType);

    /**
     * 检查自动化用例执行趋势表，今日是否同步过对应执行类型的数据
     * @param executionType 执行策略
     * @return 是 返回true，否 返回 false
     */
    boolean checkTodayHasData(int executionType,String projectId);

}
