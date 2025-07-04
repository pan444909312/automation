package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.report.AutoCaseRoiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.report.AutoExecutionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自动化用例ROI表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Service
@Slf4j
public class AutoCaseRoiServiceImpl extends ServiceImpl<AutoCaseRoiMapper, AutoCaseRoiEntity> implements AutoCaseRoiService {

    @Autowired
    AutoCaseRoiMapper autoCaseRoiMapper;

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Override
    public String getAutoCaseNameByScenarioId(String scenarioId) {
        AutoCaseRoiEntity autoCaseRoiEntity = autoCaseRoiMapper.selectOne(new QueryWrapper<AutoCaseRoiEntity>().eq("scenario_id", scenarioId));
        if (autoCaseRoiEntity == null) {
            log.info("【" + scenarioId + "】没有获取到该执行id的名称");
            return "（已删除）";
        } else {
            return autoCaseRoiEntity.getScenarioName();
        }
    }

    @Override
    public List<AutoCaseRoiEntity> selectAutoCaseRoiProjectId() {
        List<AutoCaseRoiEntity> autoCaseRoiEntities = autoCaseRoiMapper.selectAutoCaseRoiProjectId();
        return autoCaseRoiEntities;
    }

    /**
     * @return 获取所有测试场景的总节省成本的总和
     */
    @Override
    public long getAllScenarioSaveTime() {
        return 0;
    }

}
