package com.miller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.common.util.StringUtils;
import com.miller.entity.AutoCaseRoi;
import com.miller.mapper.AutoCaseRoiMapper;
import com.miller.service.AutoCaseRoiLogService;
import com.miller.service.AutoCaseRoiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.data.entity.AutoCaseRoiEntity;
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
public class AutoCaseRoiServiceImpl extends ServiceImpl<AutoCaseRoiMapper, AutoCaseRoi> implements AutoCaseRoiService {

    @Autowired
    AutoCaseRoiMapper autoCaseRoiMapper;

    @Autowired
    AutoCaseRoiLogService autoCaseRoiLogService;

    @Override
    public String getAutoCaseNameByScenarioId(String scenarioId) {
        AutoCaseRoi autoCaseRoi = autoCaseRoiMapper.selectOne(new QueryWrapper<AutoCaseRoi>().eq("scenario_id", scenarioId));
        return autoCaseRoi.getScenarioName();
    }

    // B 侧 Apifox 使用
    public boolean saveOrUpdate(AutoCaseRoiEntity entity) {
        String scenarioId = entity.getScenarioId();
        if (StringUtils.isBlank(scenarioId)) {
            log.error("scenarioId 不能为空");
            return false;
        }

        AutoCaseRoi autoCaseRoi = autoCaseRoiMapper.findByScenarioId(scenarioId);
        long currentTimeMillis = System.currentTimeMillis();

        if (!ObjectUtils.isEmpty(autoCaseRoi)) {
            autoCaseRoi.setDevelopmentTime(entity.getDevelopmentTime());
            autoCaseRoi.setManualTestTime(entity.getManualTestTime());

            // 维护成本累计 ： 已有的时间 + 新加的时间
            final int maintenanceTime =  Math.addExact(autoCaseRoi.getMaintenanceTime(), entity.getMaintenanceTime());
            autoCaseRoi.setMaintenanceTime(maintenanceTime);

            //执行次数：每次+1
            final int times = Math.addExact(autoCaseRoi.getTimes(),1);
            autoCaseRoi.setTimes(times);


            

            autoCaseRoi.setUpdateTime(currentTimeMillis);

        }else {
            autoCaseRoi = new AutoCaseRoi();
            BeanUtils.copyProperties(entity, autoCaseRoi);

            autoCaseRoi.setTimes(1);
            autoCaseRoi.setCreateTime(currentTimeMillis);
        }

        //  总节省时间 = 执行次数 * 手工测试成本
        final long saveTime = Math.multiplyExact(entity.getManualTestTime(),autoCaseRoi.getTimes());
        autoCaseRoi.setSaveTime(saveTime);

        // roi = (开发*维护)/总节省成本
        double roi = (double) autoCaseRoi.getSaveTime() / (Math.addExact(autoCaseRoi.getDevelopmentTime(), autoCaseRoi.getMaintenanceTime()));
        autoCaseRoi.setRoi(String.valueOf(roi));


        this.saveOrUpdate(autoCaseRoi);

        //  组装 auto_case_roi_log 表数据
        this.autoCaseRoiLogService.saveOrUpdate(autoCaseRoi);

        return true;
    }

}
