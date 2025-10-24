package com.miller.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailySummaryDTO;
import com.miller.entity.report.resp.AutoCaseExecutionRecordRespDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自动化用例执行记录表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */
public interface AutoExecutionRecordService extends IService<AutoExecutionRecordEntity> {

    Map<String, Object> listAutoCase(PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO);

    /**
     * 已废弃
     *
     * @param pageAutoCaseExecutionRecordReqDTO
     * @return
     */
    Map<String, Object> listAutoCaseRecord(PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO);


    boolean apifoxSaveOrUpdate(AutoCaseRoiEntity caseRoiEntity, ApifoxAutoCaseRoiDto caseRoiDto);

    List<AutoCaseExecutionRecordRespDTO> listAutoExecutionRecordWithProjectId(long startTime, long endTime, int executionType, String projectId);


    /**
     * 根据项目id、执行类型、执行状态、日期查询日执行结果
     * @param projectId
     * @param executionType
     * @param executionStatus
     * @param date
     * @return
     */
    List<AutoCaseExecutionDailyDTO> listDailyCaseExecutionResult(String projectId, int executionType, int executionStatus, Date date);

    /**
     * 根据项目id、执行类型、执行状态、日期查询日执行结果汇总（人、失败数、项目归属）
     * @param projectId
     * @param executionType
     * @param executionStatusList
     * @param date
     * @return
     */
    List<AutoCaseExecutionDailySummaryDTO> listDailyCaseExecutionResultSummary(String projectId, int executionType, List<Integer> executionStatusList, Date date);
}
