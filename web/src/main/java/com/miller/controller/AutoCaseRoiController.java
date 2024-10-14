package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.Response;
import com.miller.common.util.Result;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.dto.AddAutoCaseRoiDTO;
import com.miller.entity.dto.PageAutoCaseRoiDto;
import com.miller.entity.vo.AutoCaseRoiVo;
import com.miller.service.AutoCaseRoiService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 自动化用例ROI表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/automation/autoCaseRoi")
@Tag(name = "自动化用例roi统计")
public class AutoCaseRoiController {

    @Autowired
    AutoCaseRoiService autoCaseRoiService;

    @Operation(description = "分页查询自动化用例roi数据")
    @PostMapping("/list")
    public Result listAutoCase(@RequestBody PageAutoCaseRoiDto pageAutoCaseRoiDto) {
        System.out.println(pageAutoCaseRoiDto);
        Page<AutoCaseRoi> autoCaseRoiVoPage = new Page<>(pageAutoCaseRoiDto.getPageNo(), pageAutoCaseRoiDto.getPageSize());
        QueryWrapper<AutoCaseRoi> queryWrapper = new QueryWrapper<>();
        String executionUser = pageAutoCaseRoiDto.getExecutionUser();
        String scenarioIdOrName = pageAutoCaseRoiDto.getScenarioIdOrName();
        Date createStartTime = pageAutoCaseRoiDto.getCreateStartTime();
        Date createEndTime = pageAutoCaseRoiDto.getCreateEndTime();
        Date updateStartTime = pageAutoCaseRoiDto.getUpdateStartTime();
        Date updateEndTime = pageAutoCaseRoiDto.getUpdateEndTime();
        Integer orderBy = pageAutoCaseRoiDto.getOrderBy();
        Integer sort = pageAutoCaseRoiDto.getSort();
        if (!StringUtils.isEmpty(executionUser)) {
            queryWrapper.eq("execution_user", executionUser);
        }
        if (!StringUtils.isEmpty(scenarioIdOrName)) {
            System.out.println(scenarioIdOrName);
            queryWrapper.like("scenario_id", scenarioIdOrName).or().like("scenario_name", scenarioIdOrName);
        }
        if (createStartTime != null) {
            queryWrapper.ge("create_time", createStartTime.getTime());
        }
        if (createEndTime != null) {
            queryWrapper.le("create_time", createEndTime.getTime());
        }
        if (updateStartTime != null) {
            queryWrapper.ge("update_time", updateStartTime.getTime());
        }
        if (updateEndTime != null) {
            queryWrapper.le("update_time", updateEndTime.getTime());
        }
        if (orderBy == 1){
            queryWrapper.orderByDesc(SortEnum.getValueByKey(String.valueOf(sort)));
        }else{
            queryWrapper.orderByAsc(SortEnum.getValueByKey(String.valueOf(sort)));
        }

        Page<AutoCaseRoi> page = autoCaseRoiService.page(autoCaseRoiVoPage, queryWrapper);


        List<AutoCaseRoi> records = page.getRecords();
        long total = page.getTotal();

        ArrayList<AutoCaseRoiVo> autoCaseRoiVoList = new ArrayList<>();
        AutoCaseRoiVo autoCaseRoiVo;
        for (AutoCaseRoi record : records) {
            autoCaseRoiVo = new AutoCaseRoiVo();
            BeanUtils.copyProperties(record, autoCaseRoiVo);
            autoCaseRoiVo.setCreateTime(TimestampUtils.timestampToDate(record.getCreateTime()));
            autoCaseRoiVo.setUpdateTime(TimestampUtils.timestampToDate(record.getUpdateTime()));

            autoCaseRoiVoList.add(autoCaseRoiVo);
        }

        System.out.println(total);
        System.out.println(records);


        return Result.success().data("total", total).data("list", autoCaseRoiVoList);
    }

    @Operation(description = "添加roi")
    @PostMapping("/add")
    public Response<List<AutoCaseRoi>> addAutoCaseRoi(@RequestBody AddAutoCaseRoiDTO addAutoCaseRoiDTO) {
        AutoCaseRoi autoCaseRoi = new AutoCaseRoi();
        BeanUtils.copyProperties(addAutoCaseRoiDTO, autoCaseRoi);
        autoCaseRoiService.save(autoCaseRoi);

        return Response.success(null);
    }
}
