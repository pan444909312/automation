package com.miller.service.report;

import com.miller.entity.report.AutoCaseRoiChart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.dto.PageAutoCaseRoiChartDTO;

import java.util.Map;

/**
 * <p>
 * 测试场景总ROI表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
public interface AutoCaseRoiChartService extends IService<AutoCaseRoiChart> {

    Map<String, Object> getAutoCaseRoiChartList(PageAutoCaseRoiChartDTO pageAutoCaseRoiChartDTO);
}
