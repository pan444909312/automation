package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.constant.ProjectTypeEnum;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailySummaryDTO;
import com.miller.service.report.AutoExecutionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
@Tag(name = "自动化用例执行记录")
public class AutoExecutionRecordController {

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;


    @Operation(description = "分页查询自动化用例执行记录,测试用")
    @PostMapping("/list-test")
    public Map<String, Object> listAutoCaseForTest(@RequestBody PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO) {

//        return autoExecutionRecordService.listAutoCase(pageAutoCaseExecutionRecordReqDTO);
        return autoExecutionRecordService.listAutoCaseRecord(pageAutoCaseExecutionRecordReqDTO);
    }

    @Operation(description = "分页查询自动化用例执行记录")
    @PostMapping("/list")
    public Map<String, Object> listAutoCase(@RequestBody PageAutoCaseExecutionRecordReqDTO pageAutoCaseExecutionRecordReqDTO) {

        return autoExecutionRecordService.listAutoCase(pageAutoCaseExecutionRecordReqDTO);
    }

    /**
     * 查询执行人员，用于筛选展示用户
     *
     * @return
     */
    @Operation(description = "查询执行人员列表")
    @GetMapping("/getExecutionUserList")
    public Map<String, Object> getExecutionUserList() {

        List<AutoExecutionRecordEntity> autoExecutionRecordList = autoExecutionRecordService.list(new QueryWrapper<AutoExecutionRecordEntity>().groupBy("execution_user"));
        ArrayList<String> executionUserList = new ArrayList<>();
        autoExecutionRecordList.forEach(item -> executionUserList.add(item.getExecutionUser()));
        Map<String, Object> result = new HashMap<>();
        result.put("executionUserList", executionUserList);
        return result;
    }

    @Operation(description = "查询定时执行结果汇总")
    @PostMapping("/listDailyResultSummary")
    public Map<String, Object> listDailyResultSummary() {
//        LocalDate yesterdayLocalDate = LocalDate.now().minusDays(1);
//        Date yesterday = Date.from(yesterdayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate todayLocalDate = LocalDate.now();
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());



        List<AutoCaseExecutionDailyDTO> autoCaseExecutionDailyDTOList = autoExecutionRecordService.listDailyCaseExecutionResult(
                ProjectTypeEnum.PROJECT_C.getProjectId(), 1, 1, today);


        List<AutoCaseExecutionDailySummaryDTO> autoCaseExecutionDailySummaryDTOList = autoExecutionRecordService.listDailyCaseExecutionResultSummary(
                ProjectTypeEnum.PROJECT_C.getProjectId(), 1, 1, today);


        Map<String, Object> result = new HashMap<>();
        result.put("total",autoCaseExecutionDailyDTOList.size());
        result.put("detail", autoCaseExecutionDailyDTOList);
        result.put("summary",autoCaseExecutionDailySummaryDTOList);
        return result;

    }


}
