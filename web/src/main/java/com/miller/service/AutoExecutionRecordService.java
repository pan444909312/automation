package com.miller.service;

import com.miller.entity.AutoExecutionRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;

import java.util.Map;

/**
 * <p>
 * 自动化用例执行记录表 服务类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */
public interface AutoExecutionRecordService extends IService<AutoExecutionRecord> {

    Map<String,Object> listAutoCase(PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO);

    /**
     * 已废弃
     * @param pageAutoCaseExecutionRecordDTO
     * @return
     */
    Map<String,Object> listAutoCaseRecord(PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO);

}
