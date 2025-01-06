package com.miller.controller.report;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.entity.report.req.RemoveAutoCaseRoiReqDTO;
import com.miller.entity.util.Response;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.PageAutoCaseRoiReqDTO;
import com.miller.entity.report.resp.AutoCaseRoiRespDTO;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.common.util.TimestampUtils;
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
     * @param pageAutoCaseRoiReqDto
     * @return
     */
    @Operation(description = "分页查询自动化用例roi数据")
    @PostMapping("/list")
    public Map<String, Object> listAutoCase(@RequestBody PageAutoCaseRoiReqDTO pageAutoCaseRoiReqDto) {
        System.out.println(pageAutoCaseRoiReqDto);
        Page<AutoCaseRoiEntity> autoCaseRoiVoPage = new Page<>(pageAutoCaseRoiReqDto.getPageNo(), pageAutoCaseRoiReqDto.getPageSize());
        QueryWrapper<AutoCaseRoiEntity> queryWrapper = new QueryWrapper<>();
        String executionUser = pageAutoCaseRoiReqDto.getExecutionUser();
        String scenarioIdOrName = pageAutoCaseRoiReqDto.getScenarioIdOrName();
        Date createStartTime = pageAutoCaseRoiReqDto.getCreateStartTime();
        Date createEndTime = pageAutoCaseRoiReqDto.getCreateEndTime();
        Date updateStartTime = pageAutoCaseRoiReqDto.getUpdateStartTime();
        Date updateEndTime = pageAutoCaseRoiReqDto.getUpdateEndTime();
        Integer orderBy = pageAutoCaseRoiReqDto.getOrderBy();
        Integer sort = pageAutoCaseRoiReqDto.getSort();
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


        Page<AutoCaseRoiEntity> page = autoCaseRoiService.page(autoCaseRoiVoPage, queryWrapper);

        List<AutoCaseRoiEntity> records = page.getRecords();
        long total = page.getTotal();
        if (pageAutoCaseRoiReqDto.getIsRepeat() == 0) {
            records = new ArrayList<>(records.stream()
                    .collect(Collectors.toMap(
                            AutoCaseRoiEntity::getScenarioName, // 指定用作键的属性（这里是id）
                            Function.identity(), // 使用对象本身作为值
                            (existing, replacement) -> existing // 如果有重复，保留现有的（或选择replacement）
                    ))
                    .values());
        }


        ArrayList<AutoCaseRoiRespDTO> autoCaseRoiRespDTOList = new ArrayList<>();
        AutoCaseRoiRespDTO autoCaseRoiRespDTO;
        for (AutoCaseRoiEntity record : records) {
            autoCaseRoiRespDTO = new AutoCaseRoiRespDTO();
            BeanUtils.copyProperties(record, autoCaseRoiRespDTO);
            autoCaseRoiRespDTO.setCreateTime(TimestampUtils.timestampToDateStr(record.getCreateTime()));
            autoCaseRoiRespDTO.setUpdateTime(TimestampUtils.timestampToDateStr(record.getUpdateTime()));

            autoCaseRoiRespDTOList.add(autoCaseRoiRespDTO);
        }


        HashMap<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("list", autoCaseRoiRespDTOList);

        return result;
    }


    /**
     * 调试造数据用
     *
     * @return
     */
    @Operation(description = "添加roi")
    @PostMapping("/add")
    public Response<AutoCaseRoiEntity> addAutoCaseRoi() {
        AutoCaseRoiEntity autoCaseRoiEntity = new AutoCaseRoiEntity();
        autoCaseRoiEntity.setScenarioId(ULIDUtils.generateULID());
        autoCaseRoiService.save(autoCaseRoiEntity);

        return Response.success(autoCaseRoiEntity);
    }

    @PostMapping("/apifox/save")
    public Response<Boolean> apifoxSaveAutoCaseRoi(@RequestBody ApifoxAutoCaseRoiDto dto) {
        boolean res = autoCaseRoiService.apifoxSaveOrUpdate(dto);
        return Response.success(res);
    }


    @Operation(description = "生成测试用例场景id（ULID）")
    @PostMapping("/generateULID")
    public String generateULID() {

        return ULIDUtils.generateULID();
    }

    @Operation(description = "删除 roi")
    @PostMapping("/delete")
    public String deleteAutoCaseRoi(@RequestBody RemoveAutoCaseRoiReqDTO removeAutoCaseRoiReqDTO) {

        boolean result = autoCaseRoiService.removeById(removeAutoCaseRoiReqDTO.getId());

        return result ? "删除成功":"删除失败";
    }
}
