package com.miller.service.dashboard.impl;

import com.alibaba.fastjson.JSONObject;
import com.miller.entity.constant.PlatformTypeEnum;
import com.miller.entity.dashboard.DashBoardEntity;
import com.miller.entity.platform.Project;
import com.miller.entity.platform.User;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.mapper.report.DashboardMapper;
import com.miller.service.dashboard.DashboardService;
import dto.DashboardReqDTO;
import com.miller.service.platform.ProjectService;
import com.miller.service.platform.UserService;
import com.miller.service.report.AutoCaseRoiService;
import com.miller.service.vo.DashboardFilterOptionVO;
import com.miller.service.vo.DashboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        List<DashBoardEntity> dashBoardEntities = dashboardMapper.getAll(dashboardReqDTO);


        JSONObject jsonObject = new JSONObject();
        // 统计总数据
        DashboardVO totalData = this.statisticsDashboardData(dashBoardEntities);
        final int totalCreateCount = dashboardMapper.selectCreateCountByRangeTime(dashboardReqDTO);
        totalData.setNewCaseCount(totalCreateCount);
        totalData.setAttributionObject("total");
        jsonObject.put("totalData", totalData);

        // 渠道维度数据
        Set<Integer> platformTypeList = dashBoardEntities.stream().map(AutoCaseRoiEntity::getPlatformType).collect(Collectors.toSet());
        List<DashboardVO> platformList = new LinkedList<>();
        platformTypeList.forEach(platformType -> {
            List<DashBoardEntity> entityList = dashBoardEntities.stream()
                    .filter(autoCaseRoiEntity -> autoCaseRoiEntity.getPlatformType().equals(platformType))
                    .toList();
            DashboardVO dashboardVO = this.statisticsDashboardData(entityList);

            DashboardReqDTO reqDTO = DashboardReqDTO.init(dashboardReqDTO);
            reqDTO.setPlatforms(List.of(Long.valueOf(platformType)));
            final int platformCreateCount = dashboardMapper.selectCreateCountByRangeTime(reqDTO);
            dashboardVO.setNewCaseCount(platformCreateCount);
            dashboardVO.setAttributionObject(PlatformTypeEnum.getValueByKey(platformType));
            platformList.add(dashboardVO);
        });
        jsonObject.put("platforms", platformList);


        // 小组维度数据

        Set<String> projectIdList = dashBoardEntities.stream().map(AutoCaseRoiEntity::getProjectId).collect(Collectors.toSet());
        List<DashboardVO> groupDashBoardList = new LinkedList<>();
        projectIdList.forEach(projectId -> {
            List<DashBoardEntity> entityList = dashBoardEntities.stream()
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
        Set<String> authorList = dashBoardEntities.stream().map(AutoCaseRoiEntity::getAuthor).collect(Collectors.toSet());
        List<DashboardVO> userList = new LinkedList<>();
        authorList.forEach(author -> {
            List<DashBoardEntity> entityList = dashBoardEntities.stream()
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
            roi = BigDecimal.valueOf(cumulativeSavedCost / totalCost * 100)
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

        // 通过率和失败率（这里需要根据实际业务逻辑计算，暂时设置为0）
        // 如果有执行结果数据，可以根据成功/失败次数计算
        dashboardVO.setSuccessRate(0L);
        dashboardVO.setFailureRate(0L);

        return dashboardVO;
    }

}
