package com.miller.service.apifox;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.apifox.ApiFoxRunReportEntity;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.entity.report.req.ApifoxRunResultDTO;
import com.miller.service.apifox.enums.AttributionGroupEnum;


public interface ApiFoxRunReportService extends IService<ApiFoxRunReportEntity> {

    ApiFoxRunReportEntity converToEntity( String runId, String scenarioName, AttributionGroupEnum group,ApifoxRunResultDTO dto);

    Long saveFindId(ApiFoxRunReportEntity entity);
}


