package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseExecutionChart;
import com.miller.entity.AutoCaseIncreaseChart;
import com.miller.entity.dto.PageAutoCaseExecutionChartDTO;
import com.miller.entity.dto.PageAutoCaseIncreaseChartDTO;
import com.miller.entity.vo.AutoCaseExecutionChartVO;
import com.miller.entity.vo.AutoCaseIncreaseChartVO;
import com.miller.service.AutoCaseExecutionChartService;
import com.miller.service.AutoCaseIncreaseChartService;
import com.miller.util.TimestampUtils;
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

        Page<AutoCaseIncreaseChart> autoCaseIncreaseChartPage = autoCaseIncreaseChartService.page(page, queryWrapper);

        List<AutoCaseIncreaseChart> records = autoCaseIncreaseChartPage.getRecords();
        long total = autoCaseIncreaseChartPage.getTotal();

        List<AutoCaseIncreaseChartVO> list = new ArrayList<>();
        AutoCaseIncreaseChartVO autoCaseIncreaseChartVO;

        for (AutoCaseIncreaseChart record : records) {
            autoCaseIncreaseChartVO = new AutoCaseIncreaseChartVO();
            autoCaseIncreaseChartVO.setRemarks(record.getRemarks());
            autoCaseIncreaseChartVO.setIncreaseCase(record.getIncreaseCase());
            autoCaseIncreaseChartVO.setDate(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            list.add(autoCaseIncreaseChartVO);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",list);
        return result;
    }

}
