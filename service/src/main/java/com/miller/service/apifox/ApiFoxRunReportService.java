package com.miller.service.apifox;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.apifox.ApiFoxRunReportEntity;
import com.miller.entity.apifox.DTO.ApifoxRunResultDTO;
import com.miller.service.apifox.enums.AttributionGroupEnum;

import java.util.List;


public interface ApiFoxRunReportService extends IService<ApiFoxRunReportEntity> {

    ApiFoxRunReportEntity converToEntity( String runId, String scenarioName, AttributionGroupEnum group,ApifoxRunResultDTO dto);

    Long saveFindId(ApiFoxRunReportEntity entity);

    List<ApiFoxRunReportEntity> queryByRunId(String runId);


    /**
     * 通过 caseId 获取归属小组
     */
    String queryBelongingGroup(Long apiFoxCaseId);

    void parsingReport(AttributionGroupEnum attributionGroup);

}


