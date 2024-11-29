package com.miller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.AutoCaseRoiLog;
import com.miller.mapper.AutoCaseRoiLogMapper;
import com.miller.service.AutoCaseRoiLogService;
import com.miller.service.data.entity.AutoCaseRoiLogEntity;
import org.springframework.stereotype.Service;

@Service
public class AutoCaseRoiLogServiceImpl extends ServiceImpl<AutoCaseRoiLogMapper, AutoCaseRoiLog>  implements AutoCaseRoiLogService {


    public boolean saveOrUpdate(AutoCaseRoi autoCaseRoi){
        //  组装 auto_case_roi_log 表数据
        AutoCaseRoiLog autoCaseRoiLog = new AutoCaseRoiLog();
        autoCaseRoiLog
                .setScenarioId(autoCaseRoi.getScenarioId())
                .setExecutionUser(autoCaseRoi.getExecutionUser())
                .setManualTestTime(autoCaseRoi.getManualTestTime())
                .setMaintenanceTime(autoCaseRoi.getMaintenanceTime())
                .setDevelopmentTime(autoCaseRoi.getDevelopmentTime())
                .setSaveTime(autoCaseRoi.getSaveTime())
                .setRoi(autoCaseRoi.getRoi())
                .setCreateTime(autoCaseRoi.getCreateTime());
        return  this.saveOrUpdate(autoCaseRoiLog);
    }

}
