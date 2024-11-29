package com.miller.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.AutoCaseRoiLog;
import com.miller.entity.report.AutoCaseRoiEntity;

public interface AutoCaseRoiLogService extends IService<AutoCaseRoiLog> {

    boolean saveOrUpdate(AutoCaseRoiEntity autoCaseRoi);
}
