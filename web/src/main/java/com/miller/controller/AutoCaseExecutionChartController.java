package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseChartFutureData;
import com.miller.entity.AutoCaseExecutionChart;
import com.miller.entity.dto.PageAutoCaseExecutionChartDTO;
import com.miller.entity.vo.AutoCaseExecutionChartVO;
import com.miller.service.AutoCaseChartFutureDataService;
import com.miller.service.AutoCaseExecutionChartService;
import com.miller.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.Link;
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
    public Map<String,Object> listAutoCaseExecutionChart(@RequestBody PageAutoCaseExecutionChartDTO pageAutoCaseExecutionChartDTO){

        int pageNo = pageAutoCaseExecutionChartDTO.getPageNo();
        int pageSize = pageAutoCaseExecutionChartDTO.getPageSize();
        Date createEndTime = pageAutoCaseExecutionChartDTO.getCreateEndTime();
        Date createStartTime = pageAutoCaseExecutionChartDTO.getCreateStartTime();
        Page<AutoCaseExecutionChart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseExecutionChart> queryWrapper = new QueryWrapper<>();
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        queryWrapper.orderByDesc("create_time");

        Page<AutoCaseExecutionChart> autoCaseExecutionChartPage = autoCaseExecutionChartService.page(page, queryWrapper);

        List<AutoCaseExecutionChart> records = autoCaseExecutionChartPage.getRecords();
        long total = autoCaseExecutionChartPage.getTotal();

        LinkedList<AutoCaseExecutionChartVO> list = new LinkedList<>();
        AutoCaseExecutionChartVO autoCaseExecutionChartVO;

        for (AutoCaseExecutionChart record : records) {
            autoCaseExecutionChartVO = new AutoCaseExecutionChartVO();
            autoCaseExecutionChartVO.setRemarks(record.getRemarks());
            autoCaseExecutionChartVO.setExecutionCase(record.getExecutionCase());
            autoCaseExecutionChartVO.setDate(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            list.add(autoCaseExecutionChartVO);
        }

        //未来日期数据处理
        AutoCaseChartFutureData futureData = autoCaseChartFutureDataService.getOne(new QueryWrapper<AutoCaseChartFutureData>()
                .eq("chart_type", 3)
                .orderByDesc("future_time")
                .last("limit 1"));
        AutoCaseExecutionChartVO futureVo = new AutoCaseExecutionChartVO();
        futureVo.setExecutionCase(futureData.getExpectedExecutionCase());
        futureVo.setDate(TimestampUtils.timestampToDateStr(futureData.getFutureTime()));
        list.addFirst(futureVo);


        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",list);


        return result;
    }
}
