package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.AutoExecutionRecord;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import com.miller.service.report.AutoExecutionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> listAutoCaseForTest(@RequestBody PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO) {

//        return autoExecutionRecordService.listAutoCase(pageAutoCaseExecutionRecordDTO);
        return autoExecutionRecordService.listAutoCaseRecord(pageAutoCaseExecutionRecordDTO);
    }

    @Operation(description = "分页查询自动化用例执行记录")
    @PostMapping("/list")
    public Map<String, Object> listAutoCase(@RequestBody PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO) {

        return autoExecutionRecordService.listAutoCase(pageAutoCaseExecutionRecordDTO);
    }

    /**
     *
     * 查询执行人员，用于筛选展示用户
     *
     * @return
     */
    @Operation(description = "查询执行人员列表")
    @GetMapping("/getExecutionUserList")
    public Map<String, Object> getExecutionUserList() {

        List<AutoExecutionRecord> autoExecutionRecordList = autoExecutionRecordService.list(new QueryWrapper<AutoExecutionRecord>().groupBy("execution_user"));
        ArrayList<String> executionUserList = new ArrayList<>();
        autoExecutionRecordList.forEach(item -> executionUserList.add(item.getExecutionUser()));
        Map<String, Object> result= new HashMap<>();
        result.put("executionUserList",executionUserList);
        return result;
    }


}
