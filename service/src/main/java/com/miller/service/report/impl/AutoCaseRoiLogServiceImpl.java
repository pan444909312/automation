package com.miller.service.report.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.AutoCaseRoiLog;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.mapper.AutoCaseRoiLogMapper;
import com.miller.service.report.AutoCaseRoiLogService;
import org.springframework.stereotype.Service;

@Service
public class AutoCaseRoiLogServiceImpl extends ServiceImpl<AutoCaseRoiLogMapper, AutoCaseRoiLog>  implements AutoCaseRoiLogService {


    public boolean saveOrUpdate(AutoCaseRoiEntity autoCaseRoi){
        //  组装 auto_case_roi_log 表数据
        AutoCaseRoiLog autoCaseRoiLog = new AutoCaseRoiLog();
        autoCaseRoiLog
                .setScenarioId(autoCaseRoi.getScenarioId())
                .setExecutionUser(autoCaseRoi.getExecutionUser())
                .setManualTestTime(autoCaseRoi.getManualTestTime())
                .setMaintenanceTime(autoCaseRoi.getMaintenanceTime())
                .setDevelopmentTime(autoCaseRoi.getDevelopmentTime())
                .setSaveTime(Long.valueOf(autoCaseRoi.getSaveTime()))
                .setRoi(autoCaseRoi.getRoi())
                .setCreateTime(autoCaseRoi.getCreateTime());
        return  this.saveOrUpdate(autoCaseRoiLog);
    }

}
