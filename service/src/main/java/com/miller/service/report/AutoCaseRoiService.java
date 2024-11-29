package com.miller.service.report;

import com.miller.entity.report.AutoCaseRoi;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自动化用例ROI表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
public interface AutoCaseRoiService extends IService<AutoCaseRoi> {

    String getAutoCaseNameByScenarioId(String scenarioId);

}
