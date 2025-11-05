package com.miller.controller.report;

import com.miller.service.job.AutoCaseExecutionTask;
import com.miller.service.job.ChartDataTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 自动化用例执行趋势表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-11-05
 */
@RestController
@RequestMapping("/autoCaseChartFutureData")
@Tag(name = "测试job")
public class AutoCaseChartFutureDataController {

    @Autowired
    ChartDataTask chartDataTask;

    @Autowired
    AutoCaseExecutionTask autoCaseExecutionTask;

    @Operation(description = "定时任务测试")
    @PostMapping("/test")
    public Map<String, Object> jobTest() {
        HashMap<String, Object> result = new HashMap<>();
//        chartDataTask.initChartData();
        chartDataTask.execute();
        return result;
    }

    @Operation(description = "初始化未来数据")
    @PostMapping("/init")
    public Map<String, Object> init() {
        HashMap<String, Object> result = new HashMap<>();
//        chartDataTask.initChartData();
        chartDataTask.execute();

        return result;
    }

    @Operation(description = "定时任务测试")
    @PostMapping("/runcase-job")
    public Map<String, Object> runcase() {
        HashMap<String, Object> result = new HashMap<>();
        autoCaseExecutionTask.execute();;

        return result;
    }

}
