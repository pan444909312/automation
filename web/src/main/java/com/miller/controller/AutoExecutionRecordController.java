package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.Result;
import com.miller.entity.AutoExecutionRecord;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import com.miller.service.AutoExecutionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 自动化用例执行记录表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-10
 */
@RestController
@RequestMapping("/automation/autoExecutionRecord")
public class AutoExecutionRecordController {

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Operation(description = "分页查询自动化用例执行记录")
    @PostMapping("/list")
    public Result listAutoCase(@RequestBody PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO) {

        Page<AutoExecutionRecord> autoExecutionRecordPage = new Page<>(pageAutoCaseExecutionRecordDTO.getPageNo(), pageAutoCaseExecutionRecordDTO.getPageSize());
        QueryWrapper<AutoExecutionRecord> queryWrapperWrapper = new QueryWrapper<>();

        String scenarioId = pageAutoCaseExecutionRecordDTO.getScenarioId();

        if (!StringUtils.isEmpty(scenarioId)){
            queryWrapperWrapper.eq("scenario_id",scenarioId);
        }
        queryWrapperWrapper.orderByDesc("create_time");

        Page<AutoExecutionRecord> page = autoExecutionRecordService.page(autoExecutionRecordPage, queryWrapperWrapper);
        List<AutoExecutionRecord> records = page.getRecords();
        long total = page.getTotal();


        return Result.success().data("list",records).data("total",total);
    }

}
