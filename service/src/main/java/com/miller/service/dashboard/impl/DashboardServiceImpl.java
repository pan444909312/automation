package com.miller.service.dashboard.impl;

import com.alibaba.fastjson.JSONObject;
import com.miller.entity.constant.PlatformTypeEnum;
import com.miller.entity.dashboard.DashBoardEntity;
import com.miller.entity.platform.Project;
import com.miller.entity.platform.User;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.mapper.report.DashboardMapper;
import com.miller.service.dashboard.DashboardService;
import dto.DashboardReqDTO;
import com.miller.service.platform.ProjectService;
import com.miller.service.platform.UserService;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.service.vo.DashboardFilterOptionVO;
import com.miller.service.vo.DashboardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AutoCaseRoiService autoCaseRoiService;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public DashboardFilterOptionVO getFilterOption() {
        DashboardFilterOptionVO optionVO = new DashboardFilterOptionVO();

        // 获取用户信息选项
        List<User> userList = userService.getUserList();
        List<DashboardFilterOptionVO.Option> users = userList.stream()
                .map(user -> new DashboardFilterOptionVO.Option(user.getName(), user.getEmail()))
                .collect(Collectors.toList());
        optionVO.setUsers(users);

        // 获取平台选项
        List<DashboardFilterOptionVO.Option> platformOptions = Arrays.stream(PlatformTypeEnum.values())
                .map(value -> new DashboardFilterOptionVO.Option(value.getMessage(), value.getCode()))
                .collect(Collectors.toList());
        optionVO.setPlatform(platformOptions);

        // 获取小组选项
        List<Project> projectList = projectService.getAll();
        List<DashboardFilterOptionVO.Option> projectOptions = projectList.stream()
                .map(project -> new DashboardFilterOptionVO.Option(project.getName(), project.getProjectId()))
                .collect(Collectors.toList());
        optionVO.setGroups(projectOptions);

        return optionVO;

    }


    @Override
    public JSONObject getDashboardStatistics(DashboardReqDTO dashboardReqDTO) {

        List<DashBoardEntity> dashBoardEntityList = dashboardMapper.getAll(dashboardReqDTO);


        JSONObject jsonObject = new JSONObject();
        // 统计总数据
        DashboardVO totalData = this.statisticsDashboardData(dashBoardEntityList);
        final int totalCreateCount = dashboardMapper.selectCreateCountByRangeTime(dashboardReqDTO);
        totalData.setNewCaseCount(totalCreateCount);
        totalData.setAttributionObject("total");

        // 处理时间范围内的执行结果
        List<JSONObject> execResultList = this.getExecutionStatusList(DashboardReqDTO.init(dashboardReqDTO));
        totalData.setTimeRangeExecResult(execResultList);
        jsonObject.put("totalData", totalData);

        // 渠道维度数据
        Set<Integer> platformTypeList = dashBoardEntityList.stream().map(AutoCaseRoiEntity::getPlatformType).collect(Collectors.toSet());
        List<DashboardVO> platformList = new LinkedList<>();
        platformTypeList.forEach(platformType -> {
            List<DashBoardEntity> entityList = dashBoardEntityList.stream()
                    .filter(autoCaseRoiEntity -> autoCaseRoiEntity.getPlatformType().equals(platformType))
                    .toList();
            DashboardVO dashboardVO = this.statisticsDashboardData(entityList);

            DashboardReqDTO reqDTO = DashboardReqDTO.init(dashboardReqDTO);

            // 时间范围内的新增用例数量
            reqDTO.setPlatforms(List.of(Long.valueOf(platformType)));
            final int platformCreateCount = dashboardMapper.selectCreateCountByRangeTime(reqDTO);
            dashboardVO.setNewCaseCount(platformCreateCount);

            // 处理时间范围内的执行结果
            List<JSONObject> platformsExecResultList = this.getExecutionStatusList(reqDTO);
            dashboardVO.setTimeRangeExecResult(platformsExecResultList);

            dashboardVO.setAttributionObject(PlatformTypeEnum.getValueByKey(platformType));
            platformList.add(dashboardVO);
        });
        jsonObject.put("platforms", platformList);


        // 小组维度数据
        Set<String> projectIdList = dashBoardEntityList.stream().map(AutoCaseRoiEntity::getProjectId).collect(Collectors.toSet());
        List<DashboardVO> groupDashBoardList = new LinkedList<>();
        projectIdList.forEach(projectId -> {
            List<DashBoardEntity> entityList = dashBoardEntityList.stream()
                    .filter(autoCaseRoiEntity -> autoCaseRoiEntity.getProjectId().equals(projectId))
                    .toList();
            DashboardVO dashboardVO = this.statisticsDashboardData(entityList);

            DashboardReqDTO reqDTO = DashboardReqDTO.init(dashboardReqDTO);
            reqDTO.setProjectIds(List.of(Long.valueOf(projectId)));
            final int platformCreateCount = dashboardMapper.selectCreateCountByRangeTime(reqDTO);
            dashboardVO.setNewCaseCount(platformCreateCount);


            Project project = projectService.getById(projectId);
            dashboardVO.setAttributionObject(project.getName());
            groupDashBoardList.add(dashboardVO);
        });
        jsonObject.put("groups", groupDashBoardList);

        // 成员维度数据
        Set<String> authorList = dashBoardEntityList.stream().map(AutoCaseRoiEntity::getAuthor).collect(Collectors.toSet());
        List<DashboardVO> userList = new LinkedList<>();
        authorList.forEach(author -> {
            List<DashBoardEntity> entityList = dashBoardEntityList.stream()
                    .filter(autoCaseRoiEntity -> autoCaseRoiEntity.getAuthor().equals(author))
                    .toList();
            DashboardVO dashboardVO = this.statisticsDashboardData(entityList);

            DashboardReqDTO reqDTO = DashboardReqDTO.init(dashboardReqDTO);
            reqDTO.setEmails(List.of(author));
            final int platformCreateCount = dashboardMapper.selectCreateCountByRangeTime(reqDTO);
            dashboardVO.setNewCaseCount(platformCreateCount);


            dashboardVO.setAttributionObject(author);
            userList.add(dashboardVO);
        });
        jsonObject.put("users", userList);


        return jsonObject;
    }


    /**
     * 获取时间范围内的通过率信息
     */
    public List<JSONObject> getExecutionStatusList(DashboardReqDTO reqDTO) {
        List<DashBoardEntity> execTimeDashboardList = dashboardMapper.getExecutionStatusList(reqDTO);

        return execTimeDashboardList.stream().map(
                obj -> {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("execTime", obj.getExecTimeDay());

                    final Double successRate = BigDecimal.valueOf(obj.getSuccessCount() * 1.00 / obj.getRangeTimeExecCount() * 100L)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    jsonObject.put("successRate", successRate);

                    final Double failureRate = BigDecimal.valueOf(obj.getFailureCount() * 1.00 / obj.getRangeTimeExecCount() * 100L)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    jsonObject.put("failureRate", failureRate);

                    final Double otherErrorRate = BigDecimal.valueOf(obj.getOtherErrorCount() * 1.00 / obj.getRangeTimeExecCount() * 100L)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    jsonObject.put("otherErrorRate", otherErrorRate);
                    return jsonObject;
                }
        ).toList();
    }


    /**
     * 统计数据
     *
     * @param list
     * @return
     */
    public DashboardVO statisticsDashboardData(List<DashBoardEntity> list) {
        DashboardVO dashboardVO = new DashboardVO();

        // 用例总数
        dashboardVO.setCaseCount(list.size());

        // 执行次数（总和）
        Integer execCount = list.stream()
                .mapToInt(obj -> obj.getRangeTimeExecCount() != null ? obj.getRangeTimeExecCount() : 0)
                .sum();
        dashboardVO.setExecCount(execCount);
        log.info("总执行数量：{}", execCount);


        // 通过率
        final Integer successCount = list.stream()
                .mapToInt(obj -> obj.getSuccessCount() != null ? obj.getSuccessCount() : 0)
                .sum();
        log.info("成功数量：{}", successCount);
        final double successRate;
        if (successCount == 0 || execCount == 0) {
            successRate = 0.00;
        } else {
            successRate = BigDecimal.valueOf(successCount * 1.00 / execCount * 100L)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        dashboardVO.setSuccessRate(successRate);

        // 失败率
        final Integer failureCount = list.stream()
                .mapToInt(obj -> obj.getFailureCount() != null ? obj.getFailureCount() : 0)
                .sum();
        log.info("失败数量：{}", failureCount);
        final double failureRate ;
        if (failureCount == 0 || execCount == 0) {
            failureRate = 0.00;
        } else {
            failureRate = BigDecimal.valueOf(failureCount * 1.00 / execCount * 100L)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        dashboardVO.setFailureRate(failureRate);

        // 开发成本（总和）
        double developmentCost = list.stream()
                .mapToDouble(entity -> entity.getDevelopmentTime() != null ? entity.getDevelopmentTime() : 0)
                .sum();
        dashboardVO.setDevelopmentCost(developmentCost);

        // 维护成本（总和）
        double maintenanceCost = list.stream()
                .mapToDouble(entity -> entity.getMaintenanceTime() != null ? entity.getMaintenanceTime() : 0)
                .sum();
        dashboardVO.setMaintenanceCost(maintenanceCost);

        // 累计节省成本（总和）
        double cumulativeSavedCost = list.stream()
                .mapToDouble(entity -> entity.getManualTestTime() != null ? entity.getManualTestTime() * entity.getRangeTimeExecCount() : 0)
                .sum();
        dashboardVO.setCumulativeSavedCost(cumulativeSavedCost);

        // 计算总的 ROI
        double totalCost = developmentCost + maintenanceCost;
        double roi = 0.0;
        if (totalCost > 0) {
            roi = BigDecimal.valueOf(cumulativeSavedCost * 1.00 / totalCost * 100)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }
        dashboardVO.setRoi(roi);

        // 活跃用例数（status = 0 活跃， 1 非活跃， 2 弃用）
        long activeCaseCount = list.stream()
                .filter(entity -> entity.getStatus() != null && entity.getStatus() == 0)
                .count();
        dashboardVO.setActiveCaseCount(activeCaseCount);

        // 沉寂用例数（status = 0 活跃， 1 非活跃， 2 弃用）
        long inactiveCaseCount = list.stream()
                .filter(entity -> entity.getStatus() != null && (entity.getStatus() == 1 || entity.getStatus() == 2))
                .count();
        dashboardVO.setInactiveCaseCount(inactiveCaseCount);

        // 新增用例数（在查询时间范围内创建的用例）
        dashboardVO.setNewCaseCount(list.size());


        return dashboardVO;
    }

}
