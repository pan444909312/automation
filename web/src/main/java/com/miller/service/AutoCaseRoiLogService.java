package com.miller.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.AutoCaseRoiLog;

public interface AutoCaseRoiLogService extends IService<AutoCaseRoiLog> {

    boolean saveOrUpdate(AutoCaseRoi autoCaseRoi);
}
