package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.AutoCaseChartFutureDataEntity;
import com.miller.entity.report.req.PageAutoCaseIncreaseChartReqDTO;
import com.miller.entity.report.AutoCaseIncreaseChartEntity;
import com.miller.entity.report.resp.AutoCaseIncreaseChartRespDTO;
import com.miller.service.report.AutoCaseChartFutureDataService;
import com.miller.service.report.AutoCaseIncreaseChartService;
import com.miller.common.util.TimestampUtils;
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
    public Map<String,Object> listAutoCaseIncreaseChart(@RequestBody PageAutoCaseIncreaseChartReqDTO pageAutoCaseIncreaseChartReqDTO){

        int pageNo = pageAutoCaseIncreaseChartReqDTO.getPageNo();
        int pageSize = pageAutoCaseIncreaseChartReqDTO.getPageSize();
        Date createEndTime = pageAutoCaseIncreaseChartReqDTO.getCreateEndTime();
        Date createStartTime = pageAutoCaseIncreaseChartReqDTO.getCreateStartTime();
        Page<AutoCaseIncreaseChartEntity> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AutoCaseIncreaseChartEntity> queryWrapper = new QueryWrapper<>();
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        queryWrapper.orderByDesc("create_time");

        Page<AutoCaseIncreaseChartEntity> autoCaseIncreaseChartPage = autoCaseIncreaseChartService.page(page, queryWrapper);

        List<AutoCaseIncreaseChartEntity> records = autoCaseIncreaseChartPage.getRecords();
        long total = autoCaseIncreaseChartPage.getTotal();

        LinkedList<AutoCaseIncreaseChartRespDTO> list = new LinkedList<>();
        AutoCaseIncreaseChartRespDTO autoCaseIncreaseChartRespDTO;

        for (AutoCaseIncreaseChartEntity record : records) {
            autoCaseIncreaseChartRespDTO = new AutoCaseIncreaseChartRespDTO();
            autoCaseIncreaseChartRespDTO.setRemarks(record.getRemarks());
            autoCaseIncreaseChartRespDTO.setIncreaseCase(record.getIncreaseCase());
            autoCaseIncreaseChartRespDTO.setDate(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            list.add(autoCaseIncreaseChartRespDTO);
        }

        //未来日期数据处理
        AutoCaseChartFutureDataEntity futureData = autoCaseChartFutureDataService.getOne(new QueryWrapper<AutoCaseChartFutureDataEntity>()
                .eq("chart_type", 2)
                .orderByDesc("future_time")
                .last("limit 1"));
        AutoCaseIncreaseChartRespDTO futureVo = new AutoCaseIncreaseChartRespDTO();
        futureVo.setIncreaseCase(futureData.getExpectedIncreaseCase());
        futureVo.setDate(TimestampUtils.timestampToDateStr(futureData.getFutureTime()));

        HashMap<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("list",list);
        result.put("futureData",futureVo);
        return result;
    }

}
