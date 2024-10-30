package com.miller.controller;

import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import com.miller.service.AutoExecutionRecordService;
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


    @Operation(description = "分页查询自动化用例执行记录")
    @PostMapping("/list")
    public Map<String, Object> listAutoCase(@RequestBody PageAutoCaseExecutionRecordDTO pageAutoCaseExecutionRecordDTO) {

//        return autoExecutionRecordService.listAutoCase(pageAutoCaseExecutionRecordDTO);
        return autoExecutionRecordService.listAutoCaseRecord(pageAutoCaseExecutionRecordDTO);
    }


}
