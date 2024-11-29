package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.BasePageResponse;
import com.miller.common.util.Response;
import com.miller.entity.report.AutoCaseChartFutureDataEntity;
import com.miller.entity.report.AutoCaseExecutionChartEntity;
import com.miller.entity.report.req.PageAutoCaseExecutionChartReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionChartRespDTO;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseExecutionChartService;
import com.miller.service.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 自动化用例执行趋势表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/automation/autoCaseExecutionChart")
@Tag(name = "自动化用例执行记录统计")
public class AutoCaseExecutionChartController {

    @Autowired
    AutoCaseExecutionChartService autoCaseExecutionChartService;

    @Autowired
    AutoCaseChartFutureDataService autoCaseChartFutureDataService;

    @Operation(description = "分页查询自动化用例执行数据")
    @PostMapping("/list")
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutoCaseExecutionChartRespDTO.class)))
    public Response<BasePageResponse<AutoCaseExecutionChartRespDTO>> listAutoCaseExecutionChart(@RequestBody PageAutoCaseExecutionChartReqDTO pageAutoCaseExecutionChartDTO){

        int pageNo = pageAutoCaseExecutionChartDTO.getPageNo();
        int pageSize = pageAutoCaseExecutionChartDTO.getPageSize();
        Date createEndTime = pageAutoCaseExecutionChartDTO.getCreateEndTime();
        Date createStartTime = pageAutoCaseExecutionChartDTO.getCreateStartTime();
        Page<AutoCaseExecutionChartEntity> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseExecutionChartEntity> queryWrapper = new QueryWrapper<>();
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        queryWrapper.orderByDesc("create_time");

        Page<AutoCaseExecutionChartEntity> autoCaseExecutionChartPage = autoCaseExecutionChartService.page(page, queryWrapper);

        List<AutoCaseExecutionChartEntity> records = autoCaseExecutionChartPage.getRecords();
        long total = autoCaseExecutionChartPage.getTotal();

        LinkedList<AutoCaseExecutionChartRespDTO> list = new LinkedList<>();
        AutoCaseExecutionChartRespDTO autoCaseExecutionChartRespDTO;

        for (AutoCaseExecutionChartEntity record : records) {
            autoCaseExecutionChartRespDTO = new AutoCaseExecutionChartRespDTO();
            autoCaseExecutionChartRespDTO.setRemarks(record.getRemarks());
            autoCaseExecutionChartRespDTO.setExecutionCase(record.getExecutionCase());
            autoCaseExecutionChartRespDTO.setDate(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            list.add(autoCaseExecutionChartRespDTO);
        }

        //未来日期数据处理
        AutoCaseChartFutureDataEntity futureData = autoCaseChartFutureDataService.getOne(new QueryWrapper<AutoCaseChartFutureDataEntity>()
                .eq("chart_type", 3)
                .orderByDesc("future_time")
                .last("limit 1"));
        AutoCaseExecutionChartRespDTO futureVo = new AutoCaseExecutionChartRespDTO();
        futureVo.setExecutionCase(futureData.getExpectedExecutionCase());
        futureVo.setDate(TimestampUtils.timestampToDateStr(futureData.getFutureTime()));
        list.addFirst(futureVo);


//        HashMap<String, Object> result = new HashMap<>();
        BasePageResponse<AutoCaseExecutionChartRespDTO> response = new BasePageResponse<>(total,list);
//        result.put("total",total);
//        result.put("list",list);


        return Response.success(response);
    }
}
