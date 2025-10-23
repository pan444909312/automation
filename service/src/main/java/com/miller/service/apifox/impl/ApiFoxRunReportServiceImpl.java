package com.miller.service.apifox.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.entity.apifox.ApiFoxRunReportEntity;
import com.miller.entity.report.req.ApifoxRunResultDTO;
import com.miller.mapper.apifox.ApiFoxRunReportMapper;
import com.miller.service.apifox.ApiFoxRunReportService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ApiFoxRunReportServiceImpl extends ServiceImpl<ApiFoxRunReportMapper, ApiFoxRunReportEntity> implements ApiFoxRunReportService {

    @Override
    public ApiFoxRunReportEntity converToEntity(String runId, String name, AttributionGroupEnum group, ApifoxRunResultDTO dto) {
        if (StringUtils.isEmpty(name)) {
            name = "other";
        }

        final int totalRuns = dto.getFailCount() + dto.getSuccessCount();
        ApiFoxRunReportEntity apiFoxRunReportEntity = new ApiFoxRunReportEntity();
        apiFoxRunReportEntity
                .setRunId(runId)
                .setBelongingGroup(group.name())
                .setResponsiblePerson(name)
                .setSuccessRuns(dto.getSuccessCount())
                .setFailureRuns(dto.getFailCount())
                .setStepTotal(dto.getStepTotal())
                .setFailStepCount(dto.getFailStepCount())
                .setPassStepCount(dto.getPassStepCount())
                .setTotalRuns(totalRuns)
                .setSuccessRate((double) dto.getSuccessCount() / totalRuns)
                .setFailureRate((double) dto.getFailCount() / totalRuns)
        ;
        return apiFoxRunReportEntity;
    }

    @Override
    public Long saveFindId(ApiFoxRunReportEntity entity) {
        this.save(entity);

        /**
         * 根据 runId 和 负责人 查询唯一组件ID
         */
        QueryWrapper<ApiFoxRunReportEntity> apiFoxRunReportEntityQueryWrapper = new QueryWrapper<>();
        apiFoxRunReportEntityQueryWrapper.eq("run_id",entity.getRunId());
        apiFoxRunReportEntityQueryWrapper.eq("responsible_person",entity.getResponsiblePerson());
        ApiFoxRunReportEntity queryResult = this.getOne(apiFoxRunReportEntityQueryWrapper);
        return queryResult.getId();
    }

//    @Override
//    public Long saveFindId(ApiFoxRunReportEntity entity) {
//        //TODO 调试完成后恢复
//        return System.currentTimeMillis();
//    }
}
