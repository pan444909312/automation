package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.common.util.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.report.AutoExecutionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
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
        } else
            return autoCaseRoiEntity.getScenarioName();
    }

    // B 侧 Apifox 使用
    public boolean apifoxSaveOrUpdate(ApifoxAutoCaseRoiDto dto) {


        String scenarioId = dto.getScenarioId();
        if (StringUtils.isBlank(scenarioId)) {
            log.error("scenarioId 不能为空");
            return false;
        }

        AutoCaseRoiEntity autoCaseRoi = autoCaseRoiMapper.findByScenarioId(scenarioId);
        long currentTimeMillis = System.currentTimeMillis();

        if (!ObjectUtils.isEmpty(autoCaseRoi)) {
            autoCaseRoi.setDevelopmentTime(dto.getDevelopmentTime());
            autoCaseRoi.setManualTestTime(dto.getManualTestTime());

            // 维护成本累计 ： 已有的时间 + 新加的时间
            final int maintenanceTime = Math.addExact(autoCaseRoi.getMaintenanceTime(), dto.getMaintenanceTime());
            autoCaseRoi.setMaintenanceTime(maintenanceTime);

            //执行次数：每次+1
            final int times = Math.addExact(autoCaseRoi.getTimes(), 1);
            autoCaseRoi.setTimes(times);

            autoCaseRoi.setUpdateTime(currentTimeMillis);

        } else {
            autoCaseRoi = new AutoCaseRoiEntity();
            BeanUtils.copyProperties(dto, autoCaseRoi);

            autoCaseRoi.setTimes(1);
            autoCaseRoi.setCreateTime(currentTimeMillis);
        }

        //  总节省时间 = 执行次数 * 手工测试成本
        final long saveTime = Math.multiplyExact(dto.getManualTestTime(), autoCaseRoi.getTimes());
        autoCaseRoi.setSaveTime(saveTime);

        // roi = (开发*维护)/总节省成本
        double roi = (double) autoCaseRoi.getSaveTime() / (Math.addExact(autoCaseRoi.getDevelopmentTime(), autoCaseRoi.getMaintenanceTime()));
        autoCaseRoi.setRoi(String.valueOf(roi));

        // 写入数据
        this.saveOrUpdate(autoCaseRoi);


        return this.autoExecutionRecordService.apifoxSaveOrUpdate(autoCaseRoi, dto);
    }

    /**
     * @return 获取所有测试场景的总节省成本的总和
     */
    @Override
    public long getAllScenarioSaveTime() {
        return 0;
    }

}
