package com.miller.mapper.report;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailySummaryDTO;
import com.miller.entity.report.resp.AutoCaseExecutionRecordRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 自动化用例执行记录表 Mapper 接口
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */

@Mapper
public interface AutoExecutionRecordMapper extends BaseMapper<AutoExecutionRecordEntity> {


    Page<AutoExecutionRecordEntity> selectPageByCondition(Page<?> page, @Param("req") PageAutoCaseExecutionRecordReqDTO req);

    List<AutoCaseExecutionRecordRespDTO> selectAutoExecutionRecordWithProjectId(long startTime, long endTime, int executionType, String projectId);


    List<AutoCaseExecutionDailyDTO> selectDailyCaseExecutionResult(String projectId, int executionType, int executionStatus, Date date);

    List<AutoCaseExecutionDailySummaryDTO> selectDailyCaseExecutionResultSummary(String projectId, int executionType, int executionStatus, Date date);

}
