package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.report.resp.AutoCaseRoiChartRespDTO;
import com.miller.entity.util.BasePageResponse;
import com.miller.entity.util.Response;
import com.miller.entity.report.AutoCaseChartFutureDataEntity;
import com.miller.entity.report.AutoCaseExecutionChartEntity;
import com.miller.entity.report.req.PageAutoCaseExecutionChartReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionChartRespDTO;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseExecutionChartService;
import com.miller.common.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public Response<BasePageResponse<AutoCaseExecutionChartRespDTO>> listAutoCaseExecutionChart(@RequestBody PageAutoCaseExecutionChartReqDTO pageAutoCaseExecutionChartDTO) {


        int pageNo = pageAutoCaseExecutionChartDTO.getPageNo();
        // 分页的size，需要按执行策略的枚举类型乘上去，因为是按执行策略保存，不然会可能会差出来当天缺少某几个执行策略的数据
        // 4条数据为一组，最后返回为一条数据
        int pageSize = pageAutoCaseExecutionChartDTO.getPageSize() * ExecutionTypeEnum.values().length;
        Page<AutoCaseExecutionChartEntity> page = new Page<>(pageNo, pageSize);

        QueryWrapper<AutoCaseExecutionChartEntity> queryWrapper = new QueryWrapper<>();
        Date createEndTime = pageAutoCaseExecutionChartDTO.getCreateEndTime();
        Date createStartTime = pageAutoCaseExecutionChartDTO.getCreateStartTime();
        List<Integer> executionTypeList = pageAutoCaseExecutionChartDTO.getExecutionTypeList();
        if (executionTypeList == null){
            //如果为空则默认 查所有类型
            executionTypeList = new ArrayList<>();
            for (ExecutionTypeEnum item : ExecutionTypeEnum.values()) {
                executionTypeList.add(item.getCode());
            }
        }
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
//        if (executionTypeList != null && !executionTypeList.isEmpty()) {
//            queryWrapper.in("execution_type", executionTypeList);
//        }
        queryWrapper.orderByDesc("create_time");

        Page<AutoCaseExecutionChartEntity> autoCaseExecutionChartPage = autoCaseExecutionChartService.page(page, queryWrapper);

        List<AutoCaseExecutionChartEntity> records = autoCaseExecutionChartPage.getRecords();
        // 总数需要除以执行策略的选择个数，不然算出来的总个数是所有日期的ROI乘执行策略个人的总数
        long total = autoCaseExecutionChartPage.getTotal()/ ExecutionTypeEnum.values().length;

        //数据组装

        LinkedList<AutoCaseExecutionChartRespDTO> list = new LinkedList<>();
        AutoCaseExecutionChartRespDTO autoCaseExecutionChartRespDTO;
        int executionCaseSum = 0;
        String lastChartDate = records.get(0).getChartDate();
        for (AutoCaseExecutionChartEntity record : records) {
            if (!lastChartDate.equals(record.getChartDate())) {

                autoCaseExecutionChartRespDTO = new AutoCaseExecutionChartRespDTO();
                autoCaseExecutionChartRespDTO.setRemarks("");
                autoCaseExecutionChartRespDTO.setExecutionCase(executionCaseSum);
                autoCaseExecutionChartRespDTO.setDate(lastChartDate);
                list.add(autoCaseExecutionChartRespDTO);

                // 初始化下一组数据
                lastChartDate = record.getChartDate();
                executionCaseSum = 0;
            }
            if (executionTypeList.contains(record.getExecutionType())) {
                executionCaseSum = executionCaseSum + record.getExecutionCase();
            }
        }
        list.add(new AutoCaseExecutionChartRespDTO(executionCaseSum,"",lastChartDate));

        //未来日期数据处理
        QueryWrapper<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataQueryWrapper = new QueryWrapper<>();
        autoCaseChartFutureDataQueryWrapper.eq("chart_type", 3);
        if (!executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.in("execution_type", executionTypeList);
        }
        autoCaseChartFutureDataQueryWrapper.orderByDesc("future_time");
        if (!executionTypeList.isEmpty()) {
            autoCaseChartFutureDataQueryWrapper.last("limit " + executionTypeList.size());
        }

        List<AutoCaseChartFutureDataEntity> autoCaseChartFutureDataEntityList = autoCaseChartFutureDataService.list(autoCaseChartFutureDataQueryWrapper);
        AutoCaseExecutionChartRespDTO futureVo = new AutoCaseExecutionChartRespDTO();
        int sum = 0;
        for (AutoCaseChartFutureDataEntity futureData : autoCaseChartFutureDataEntityList) {
            sum = sum + futureData.getExpectedExecutionCase();
        }
        futureVo.setExecutionCase(sum);
        futureVo.setDate(TimestampUtils.timestampToDateStr(autoCaseChartFutureDataEntityList.get(0).getFutureTime()));
        list.addFirst(futureVo);


//        HashMap<String, Object> result = new HashMap<>();
        BasePageResponse<AutoCaseExecutionChartRespDTO> response = new BasePageResponse<>(total, list);
//        result.put("total",total);
//        result.put("list",list);


        return Response.success(response);
    }
}
