package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.AutoCaseChartFutureData;
import com.miller.entity.report.AutoCaseIncreaseChart;
import com.miller.entity.dto.PageAutoCaseIncreaseChartDTO;
import com.miller.entity.report.vo.AutoCaseIncreaseChartVO;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseIncreaseChartService;
import com.miller.service.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 自动化用例增长趋势表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/automation/autoCaseIncreaseChart")
@Tag(name = "自动化用例新增记录统计")
public class AutoCaseIncreaseChartController {

    @Autowired
    AutoCaseIncreaseChartService autoCaseIncreaseChartService;

    @Autowired
    AutoCaseChartFutureDataService autoCaseChartFutureDataService;

    @Operation(description = "分页查询自动化用例新增数据")
    @PostMapping("/list")
    public Map<String,Object> listAutoCaseIncreaseChart(@RequestBody PageAutoCaseIncreaseChartDTO pageAutoCaseIncreaseChartDTO){

        int pageNo = pageAutoCaseIncreaseChartDTO.getPageNo();
        int pageSize = pageAutoCaseIncreaseChartDTO.getPageSize();
        Date createEndTime = pageAutoCaseIncreaseChartDTO.getCreateEndTime();
        Date createStartTime = pageAutoCaseIncreaseChartDTO.getCreateStartTime();
        Page<AutoCaseIncreaseChart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseIncreaseChart> queryWrapper = new QueryWrapper<>();
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        queryWrapper.orderByDesc("create_time");

        Page<AutoCaseIncreaseChart> autoCaseIncreaseChartPage = autoCaseIncreaseChartService.page(page, queryWrapper);

        List<AutoCaseIncreaseChart> records = autoCaseIncreaseChartPage.getRecords();
        long total = autoCaseIncreaseChartPage.getTotal();

        LinkedList<AutoCaseIncreaseChartVO> list = new LinkedList<>();
        AutoCaseIncreaseChartVO autoCaseIncreaseChartVO;

        for (AutoCaseIncreaseChart record : records) {
            autoCaseIncreaseChartVO = new AutoCaseIncreaseChartVO();
            autoCaseIncreaseChartVO.setRemarks(record.getRemarks());
            autoCaseIncreaseChartVO.setIncreaseCase(record.getIncreaseCase());
            autoCaseIncreaseChartVO.setDate(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            list.add(autoCaseIncreaseChartVO);
        }

        //未来日期数据处理
        AutoCaseChartFutureData futureData = autoCaseChartFutureDataService.getOne(new QueryWrapper<AutoCaseChartFutureData>()
                .eq("chart_type", 2)
                .orderByDesc("future_time")
                .last("limit 1"));
        AutoCaseIncreaseChartVO futureVo = new AutoCaseIncreaseChartVO();
        futureVo.setIncreaseCase(futureData.getExpectedIncreaseCase());
        futureVo.setDate(TimestampUtils.timestampToDateStr(futureData.getFutureTime()));
        list.addFirst(futureVo);

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",list);
        return result;
    }

}
