package com.miller.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.common.util.DateUtils;
import com.miller.entity.platform.User;
import com.miller.entity.platform.UserBindProject;
import com.miller.entity.report.req.*;
import com.miller.entity.util.Response;
import com.miller.common.util.ULIDUtils;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.constant.SortEnum;
import com.miller.entity.report.resp.AutoCaseRoiRespDTO;
import com.miller.mapper.platform.UserMapper;
import com.miller.service.platform.ProjectService;
import com.miller.service.platform.UserBindProjectService;
import com.miller.service.report.ApifoxAutoCaseRoiService;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.common.util.TimestampUtils;
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
    UserMapper userMapper;

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
        String projectId = pageAutoCaseRoiReqDto.getProjectId();
        String author = pageAutoCaseRoiReqDto.getAuthor();
        Integer platformType = pageAutoCaseRoiReqDto.getPlatformType();
        String creator = pageAutoCaseRoiReqDto.getCreator();
        if (!StringUtils.isEmpty(author)) {
            queryWrapper.like("author", author);
        }
        if (!StringUtils.isEmpty(platformType)) {
            queryWrapper.eq("platform_type", platformType);
        }
        if (!StringUtils.isEmpty(creator)) {
            queryWrapper.like("creator", creator);
        }
        if (!StringUtils.isEmpty(projectId)) {
            queryWrapper.eq("project_id", projectId);
        }
        if (!StringUtils.isEmpty(executionUser)) {
            queryWrapper.eq("execution_user", executionUser);
        }
        if (!StringUtils.isEmpty(scenarioIdOrName)) {
            queryWrapper.and(item -> item.like("scenario_id", scenarioIdOrName).or().like("scenario_name", scenarioIdOrName));
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
     * 新增用例
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

    @Operation(description = "ui-ios自动化执行保存自动化数据")
    @PostMapping("/saveData/ios")
    public Response<String> uiAddAutoCaseRoi(@RequestBody UiAutoCaseRoiReqDTO autoCaseRoiReqDTO) {

        try {
            checkBaseData(autoCaseRoiReqDTO);
            boolean flag = autoCaseRoiService.iosAutoCaseSaveOrUpdate(autoCaseRoiReqDTO);
            if (flag)
                return Response.success("保存成功");
            else
                return Response.fail("保存失败");
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }

    }

    @Operation(description = "ui-android自动化执行保存自动化数据")
    @PostMapping("/saveData/android")
    public Response<String> androidAddAutoCaseRoi(@RequestBody UiAutoCaseRoiReqDTO autoCaseRoiReqDTO) {

        try {
            checkBaseData(autoCaseRoiReqDTO);
            boolean flag = autoCaseRoiService.androidAutoCaseSaveOrUpdate(autoCaseRoiReqDTO);
            if (flag)
                return Response.success("保存成功");
            else
                return Response.fail("保存失败");
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }

    }

    @Operation(description = "ui-web自动化执行保存自动化数据")
    @PostMapping("/saveData/web")
    public Response<String> webAddAutoCaseRoi(@RequestBody UiAutoCaseRoiReqDTO autoCaseRoiReqDTO) {

        try {
            checkBaseData(autoCaseRoiReqDTO);
            boolean flag = autoCaseRoiService.webAutoCaseSaveOrUpdate(autoCaseRoiReqDTO);
            if (flag)
                return Response.success("保存成功");
            else
                return Response.fail("保存失败");
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }

    }

    @Operation(description = "jmeter自动化执行保存自动化数据")
    @PostMapping("/saveData/jmeter")
    public Response<String> jmeterAddAutoCaseRoi(@RequestBody JmeterAutoCaseRoiReqDTO autoCaseRoiReqDTO) {

        try {
            checkBaseData(autoCaseRoiReqDTO);
            boolean flag = autoCaseRoiService.jmeterAutoCaseSaveOrUpdate(autoCaseRoiReqDTO);
            if (flag)
                return Response.success("保存成功");
            else
                return Response.fail("保存失败");
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    private void checkBaseData(AutoCaseRoiReqDTO autoCaseRoiReqDTO) throws Exception {
        String author = autoCaseRoiReqDTO.getAuthor();

        //  校验是否有此实现人
        if (ObjectUtils.isEmpty(author)) {
            throw new Exception("author 必填，不然Case 无法归属用户");
        }
        User user = userMapper.selectByEmail(author);
        if (ObjectUtils.isEmpty(user)) {
            throw new Exception("查不到该用户:" + autoCaseRoiReqDTO.getAuthor() + ",请联系开发添加");
        }

        // 查询成员归属的项目ID
        UserBindProject userBindProject = userBindProjectService.selectByUserId(user.getUserId());
        if (ObjectUtils.isEmpty(userBindProject)) {
            throw new Exception("该用户没有归属项目:" + autoCaseRoiReqDTO.getAuthor() + ",请联系开发添加");
        }
        autoCaseRoiReqDTO.setProjectId(userBindProject.getProjectId());
    }


    @PostMapping("/apifox/save")
    @Transactional
    public Response<String> apifoxSaveAutoCaseRoi(@RequestBody ApifoxAutoCaseRoiDto dto) {


        // 去除默认归属项目配置
//        Project project = null;
//        if (!ObjectUtils.isEmpty(dto.getDept())) {
//            project = projectService.findByName(dto.getDept());
//        }
//        if (ObjectUtils.isEmpty(project)){
//            log.error("查不到项目：{}，默认使用 B-商家组 查询",dto.getDept());
//            project = projectService.findByName("B端-商家组");
//        }



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


        // 查询成员归属的项目ID
        UserBindProject userBindProject = userBindProjectService.selectByUserId(user.getUserId());
        if (ObjectUtils.isEmpty(userBindProject)) {
            return Response.fail("该用户没有归属项目:" + dto.getAuthor() + ",请联系开发添加");
        }
        dto.setProjectId(userBindProject.getProjectId());


        // 场景 ID 为空
        if (com.miller.common.util.StringUtils.isBlank(dto.getScenarioId())) {
            return Response.fail("scenarioId 不能为空");
        }
        apifoxAutoCaseRoiService.apifoxSaveOrUpdate(dto);

        return Response.success("收到请求",null);
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

    @Operation(description = "手动批量更新projectId")
    @PostMapping("/updateProjectId")
    public Map<String, Object> updateProjectId() {
        List<AutoCaseRoiEntity> autoCaseRoiEntities = autoCaseRoiService.selectAutoCaseRoiProjectId();
        autoCaseRoiService.updateBatchById(autoCaseRoiEntities);


        HashMap<String, Object> result = new HashMap<>();
        result.put("result", autoCaseRoiEntities);

        return result;
    }

    @Operation(description = "手动触发检索更新用例的活跃状态")
    @PostMapping("/updateCaseActive")
    public Response<String> updateCaseActive() {
        Integer i = autoCaseRoiService.updateCaseActive();

        return Response.success("更新了" + i + "条数据");
    }


}
