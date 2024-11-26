package com.miller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.Response;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.AutoCaseRoi;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.dto.AddAutoCaseRoiDTO;
import com.miller.entity.dto.ApifoxAutoCaseRoiDto;
import com.miller.entity.dto.PageAutoCaseRoiDTO;
import com.miller.entity.vo.AutoCaseRoiVO;
import com.miller.service.AutoCaseRoiService;
import com.miller.service.data.entity.AutoCaseRoiEntity;
import com.miller.util.TimestampUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 分页查询自动化用例roi数据
     *
     * @param pageAutoCaseRoiDto
     * @return
     */
    @Operation(description = "分页查询自动化用例roi数据")
    @PostMapping("/list")
    public Map<String, Object> listAutoCase(@RequestBody PageAutoCaseRoiDTO pageAutoCaseRoiDto) {
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
        if (orderBy == 1) {
            queryWrapper.orderByDesc(SortEnum.getValueByKey(String.valueOf(sort)));
        } else {
            queryWrapper.orderByAsc(SortEnum.getValueByKey(String.valueOf(sort)));
        }


        Page<AutoCaseRoi> page = autoCaseRoiService.page(autoCaseRoiVoPage, queryWrapper);

        List<AutoCaseRoi> records = page.getRecords();
        long total = page.getTotal();
        if (pageAutoCaseRoiDto.getIsRepeat() == 0) {
            records = new ArrayList<>(records.stream()
                    .collect(Collectors.toMap(
                            AutoCaseRoi::getScenarioName, // 指定用作键的属性（这里是id）
                            Function.identity(), // 使用对象本身作为值
                            (existing, replacement) -> existing // 如果有重复，保留现有的（或选择replacement）
                    ))
                    .values());
        }


        ArrayList<AutoCaseRoiVO> autoCaseRoiVoList = new ArrayList<>();
        AutoCaseRoiVO autoCaseRoiVo;
        for (AutoCaseRoi record : records) {
            autoCaseRoiVo = new AutoCaseRoiVO();
            BeanUtils.copyProperties(record, autoCaseRoiVo);
            autoCaseRoiVo.setCreateTime(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            autoCaseRoiVo.setUpdateTime(TimestampUtils.timestampToDateStr(record.getUpdateTime()));

            autoCaseRoiVoList.add(autoCaseRoiVo);
        }


        HashMap<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("list", autoCaseRoiVoList);

        return result;
    }


    /**
     * 调试造数据用
     *
     * @param addAutoCaseRoiDTO
     * @return
     */
    @Operation(description = "添加roi")
    @PostMapping("/add")
    public Response<List<AutoCaseRoi>> addAutoCaseRoi(@RequestBody AddAutoCaseRoiDTO addAutoCaseRoiDTO) {
        AutoCaseRoi autoCaseRoi = new AutoCaseRoi();
        BeanUtils.copyProperties(addAutoCaseRoiDTO, autoCaseRoi);
        autoCaseRoiService.save(autoCaseRoi);

        return Response.success(null);
    }

    @PostMapping("/apifox/save")
    public Response<Boolean> apifoxSaveAutoCaseRoi(@RequestBody ApifoxAutoCaseRoiDto dto) {
        AutoCaseRoiEntity entity = new AutoCaseRoiEntity();
        BeanUtils.copyProperties(dto, entity);
        boolean res = autoCaseRoiService.saveOrUpdate(entity);
        return Response.success(res);
    }


    @Operation(description = "生成测试用例场景id（ULID）")
    @PostMapping("/generateULID")
    public String addAutoCaseRoi() {

        return ULIDUtils.generateULID();
    }

    @Operation(description = "删除 roi")
    @PostMapping("/delete")
    public String deleteAutoCaseRoi(@RequestParam("id") Integer id) {

        boolean result = autoCaseRoiService.removeById(id);

        return result ? "删除成功" : "删除失败";
    }


}
