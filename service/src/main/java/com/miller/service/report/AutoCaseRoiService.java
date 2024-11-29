package com.miller.service.report;

import com.miller.entity.report.AutoCaseRoiEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.service.data.entity.AutoCaseRoiEntity;

/**
 * <p>
 * 自动化用例ROI表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
public interface AutoCaseRoiService extends IService<AutoCaseRoiEntity> {

    String getAutoCaseNameByScenarioId(String scenarioId);

    boolean saveOrUpdate(AutoCaseRoiEntity entity);
}
