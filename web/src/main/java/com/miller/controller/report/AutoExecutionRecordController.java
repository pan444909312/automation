package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.constant.ProjectTypeEnum;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.entity.report.req.ListDailyResultSummaryReqDTO;
import com.miller.entity.report.req.ListRecentExecutionRecordReqDTO;
import com.miller.entity.report.req.PageAutoCaseExecutionRecordReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDataDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailySummaryDTO;
import com.miller.service.report.AutoExecutionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
    public Map<String, Object> listDailyResultSummary(@RequestBody ListDailyResultSummaryReqDTO reqDTO) {
        String projectId = reqDTO.getProjectId();
        String date = reqDTO.getDate();
        LocalDate todayLocalDate;

        if (date != null && !date.isEmpty()){
            todayLocalDate = LocalDate.parse(reqDTO.getDate());
        }else {
            todayLocalDate = LocalDate.now();
        }

        // 校验projectId在枚举内
        if (projectId != null && !projectId.isEmpty()){
            projectId = ProjectTypeEnum.getValueByKey(Integer.parseInt(projectId));
        }

        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        // 查询detail
        List<AutoCaseExecutionDailyDTO> autoCaseExecutionDailyDTOList = autoExecutionRecordService.listDailyCaseExecutionResult(
                projectId,
                ExecutionTypeEnum.DAILY_CHECK.getCode(),
                ExecutionStatusEnum.SUCCESS.getCode(),
                today
        );

//        ArrayList<Integer> executionStatusList = new ArrayList<>();
//        executionStatusList.add(ExecutionStatusEnum.FAIL.getCode());
//        executionStatusList.add(ExecutionStatusEnum.PASS.getCode());
//        executionStatusList.add(ExecutionStatusEnum.ERROR.getCode());



        Map<String, Object> result = new HashMap<>();
        result.put("total", autoCaseExecutionDailyDTOList.size());
        result.put("detail", autoCaseExecutionDailyDTOList);
        result.put("summary", autoExecutionRecordService.getDailyCaseExecutionSummaryByPerson(projectId, today));
        return result;

    }

    @Operation(description = "根据条件查询最近20条执行记录")
    @PostMapping("/listRecentRecords")
    public Map<String, Object> listRecentExecutionRecords(@RequestBody ListRecentExecutionRecordReqDTO listRecentExecutionRecordReqDTO) {
        return autoExecutionRecordService.listRecentExecutionRecords(listRecentExecutionRecordReqDTO);
    }

}
