package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.AutoExecutionRecord;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.dto.PageAutoCaseExecutionRecordDTO;
import com.miller.entity.vo.AutoCaseExecutionRecordVO;
import com.miller.mapper.AutoExecutionRecordMapper;
import com.miller.service.AutoCaseRoiService;
import com.miller.service.AutoExecutionRecordService;
import com.miller.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
