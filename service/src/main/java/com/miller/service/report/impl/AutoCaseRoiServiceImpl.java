package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoCaseRoi;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.report.AutoCaseRoiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 自动化用例ROI表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Service
public class AutoCaseRoiServiceImpl extends ServiceImpl<AutoCaseRoiMapper, AutoCaseRoi> implements AutoCaseRoiService {

    @Autowired
    AutoCaseRoiMapper autoCaseRoiMapper;
    @Override
    public String getAutoCaseNameByScenarioId(String scenarioId) {
        AutoCaseRoi autoCaseRoi = autoCaseRoiMapper.selectOne(new QueryWrapper<AutoCaseRoi>().eq("scenario_id", scenarioId));
        return autoCaseRoi.getScenarioName();
    }
}
