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


    /**
     *
     * @param page
     * @param req
     * @return
     */
    Page<AutoExecutionRecordEntity> selectPageByCondition(Page<?> page, @Param("req") PageAutoCaseExecutionRecordReqDTO req);

    /**
     * 获取项目下指定时间段内指定执行类型的用例执行结果
     * @param startTime
     * @param endTime
     * @param executionType
     * @param projectId
     * @return
     */
    List<AutoCaseExecutionRecordRespDTO> selectAutoExecutionRecordWithProjectId(long startTime, long endTime, int executionType, String projectId);


    /**
     *  获取每日执行失败的用例数据
     * @param projectId
     * @param executionType
     * @param executionStatus
     * @param date
     * @return
     */
    List<AutoCaseExecutionDailyDTO> selectDailyCaseExecutionResult(String projectId, int executionType, int executionStatus, Date date);

    /**
     * 按时间 项目 执行类型 获取每日执行结果统计数据 以人分组
     * @param projectId
     * @param executionType
     * @param executionStatusList
     * @param date
     * @return
     */
    List<AutoCaseExecutionDailySummaryDTO> selectDailyCaseExecutionResultSummary(String projectId, int executionType, @Param("executionStatusList") List<Integer> executionStatusList, Date date);

}
