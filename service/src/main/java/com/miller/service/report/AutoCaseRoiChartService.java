package com.miller.service.report;

import com.miller.entity.report.AutoCaseRoiChartEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.req.PageAutoCaseRoiChartReqDTO;

import java.util.Map;

/**
 * <p>
 * 测试场景总ROI表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
public interface AutoCaseRoiChartService extends IService<AutoCaseRoiChartEntity> {

    /**
     * 条件分页查询测试场景总ROI表
     * @param pageAutoCaseRoiChartReqDTO 条件分页查询测试场景总ROI表
     * @return
     */
    Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartReqDTO pageAutoCaseRoiChartReqDTO);


    /**
     * 获取当前累计的开发成本
     * @return
     */
    long getTotalDevelopTime(int executionType);

    /**
     * 获取当前累计的维护成本
     * @return
     */
    long getTotalMaintenanceTime(int executionType);

    /**
     * 获取当前累计的执行次数
     * @return
     */
    int getTotalTimes(int executionType);

    /**
     * 获取当前累计的收益
     * @return
     */
    long getTotalSaveTime(int executionType);

    /**
     * 检查自动化用例执行趋势表，今日是否同步过对应执行类型的数据
     * @param executionType 执行策略
     * @return 是 返回true，否 返回 false
     */
    boolean checkTodayHasData(int executionType);

}
