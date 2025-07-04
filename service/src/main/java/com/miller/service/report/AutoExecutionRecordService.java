package com.miller.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionRecordRespDTO;

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

}
