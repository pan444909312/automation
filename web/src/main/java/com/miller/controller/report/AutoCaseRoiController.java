package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.DateUtils;
import com.miller.entity.platform.Dept;
import com.miller.entity.platform.Project;
import com.miller.entity.platform.User;
import com.miller.entity.report.req.RemoveAutoCaseRoiReqDTO;
import com.miller.entity.util.Response;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.report.req.ApifoxAutoCaseRoiDto;
import com.miller.entity.report.req.PageAutoCaseRoiReqDTO;
import com.miller.entity.report.resp.AutoCaseRoiRespDTO;
import com.miller.mapper.platform.DeptMapper;
import com.miller.mapper.platform.ProjectMapper;
import com.miller.mapper.platform.UserMapper;
import com.miller.service.platform.ProjectService;
import com.miller.service.platform.UserBindProjectService;
import com.miller.service.report.ApifoxAutoCaseRoiService;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.common.util.TimestampUtils;
import com.miller.service.report.UserBindDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
public class AutoCaseRoiController {

    @Autowired
    AutoCaseRoiService autoCaseRoiService;

    @Autowired
    ApifoxAutoCaseRoiService apifoxAutoCaseRoiService;

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserBindDeptService userBindDeptService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserBindProjectService userBindProjectService;


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
        Date createStartTime = DateUtils.strToDate(pageAutoCaseRoiReqDto.getCreateStartTime(), "yyyy-MM-dd");
        Date createEndTime = DateUtils.strToDate(pageAutoCaseRoiReqDto.getCreateEndTime(), "yyyy-MM-dd");
        Date updateStartTime = DateUtils.strToDate(pageAutoCaseRoiReqDto.getUpdateStartTime(), "yyyy-MM-dd");
        Date updateEndTime = DateUtils.strToDate(pageAutoCaseRoiReqDto.getUpdateEndTime(), "yyyy-MM-dd");
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
            queryWrapper.le("create_time", createEndTime.getTime() + 1000 * 60 * 60 * 24);
        }
        if (updateStartTime != null) {
            queryWrapper.ge("update_time", updateStartTime.getTime());
        }
        if (updateEndTime != null) {
            queryWrapper.le("update_time", updateEndTime.getTime() + 1000 * 60 * 60 * 24);
        }
        if (pageAutoCaseRoiReqDto.getIsRepeat() == 0) {
            queryWrapper.groupBy("scenario_name");
        }
        if (orderBy == 1) {
            queryWrapper.orderByDesc(SortEnum.getValueByKey(String.valueOf(sort)));
        } else {
            queryWrapper.orderByAsc(SortEnum.getValueByKey(String.valueOf(sort)));
        }


        Page<AutoCaseRoiEntity> page = autoCaseRoiService.page(autoCaseRoiVoPage, queryWrapper);

        List<AutoCaseRoiEntity> records = page.getRecords();
        long total = page.getTotal();

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
    @Transactional
    public Response<Boolean> apifoxSaveAutoCaseRoi(@RequestBody ApifoxAutoCaseRoiDto dto) {

        /**
         //  校验小组归属小组是否存在
         Dept dept;
         if (!ObjectUtils.isEmpty(dto.getDept())) {
         dept = deptMapper.selectByName(dto.getDept());
         }else {
         dept = deptMapper.selectByName("B-商家组");
         }
         ***/

        Project project = null;
        if (!ObjectUtils.isEmpty(dto.getDept())) {
            project = projectService.findByName(dto.getDept());
        }
        if (ObjectUtils.isEmpty(project)){
            log.error("查不到项目：{}，默认使用 B-商家组 查询",dto.getDept());
            project = projectService.findByName("B端-商家组");
        }



        //  校验是否有此实现人
        String author = ObjectUtils.isNotEmpty(dto.getAuthor()) ? dto.getAuthor() : dto.getExecutionUser();
        if (ObjectUtils.isEmpty(author)) {
            return Response.fail("executionUser 和 author 必填一个，不然Case 无法归属用户");
        }
        User user = userMapper.selectByName(author);
        if (ObjectUtils.isEmpty(user)) {
            return Response.fail("查不到该用户:" + dto.getAuthor() + ",请联系开发添加");
        }
        dto.setEmail(user.getEmail());

        // 场景 ID 为空
        if (com.miller.common.util.StringUtils.isBlank(dto.getScenarioId())) {
            return Response.fail("scenarioId 不能为空");
        }

        boolean res = apifoxAutoCaseRoiService.apifoxSaveOrUpdate(dto);

        // 校验 user 和 dept 是否有映射关系，没有则创建一个
        userBindProjectService.saveOrUpdate(project.getProjectId(), user.getUserId());

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

        return result ? "删除成功" : "删除失败";
    }
}
