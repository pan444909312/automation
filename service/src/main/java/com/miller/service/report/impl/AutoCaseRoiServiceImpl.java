package com.miller.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.constant.PlatformTypeEnum;
import com.miller.entity.platform.User;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.entity.report.req.JmeterAutoCaseRoiReqDTO;
import com.miller.entity.report.req.UiAutoCaseRoiReqDTO;
import com.miller.mapper.report.AutoCaseRoiMapper;
import com.miller.service.platform.UserService;
import com.miller.service.report.AutoCaseRoiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.report.AutoExecutionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 自动化用例ROI表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Service
@Slf4j
public class AutoCaseRoiServiceImpl extends ServiceImpl<AutoCaseRoiMapper, AutoCaseRoiEntity> implements AutoCaseRoiService {

    @Autowired
    AutoCaseRoiMapper autoCaseRoiMapper;

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Autowired
    UserService userService;

    @Override
    public String getAutoCaseNameByScenarioId(String scenarioId) {
        AutoCaseRoiEntity autoCaseRoiEntity = autoCaseRoiMapper.selectOne(new QueryWrapper<AutoCaseRoiEntity>().eq("scenario_id", scenarioId));
        if (autoCaseRoiEntity == null) {
            log.info("【" + scenarioId + "】没有获取到该执行id的名称");
            return "（已删除）";
        } else {
            return autoCaseRoiEntity.getScenarioName();
        }
    }

    @Override
    public List<AutoCaseRoiEntity> selectAutoCaseRoiProjectId() {
        List<AutoCaseRoiEntity> autoCaseRoiEntities = autoCaseRoiMapper.selectAutoCaseRoiProjectId();
        return autoCaseRoiEntities;
    }

    @Override
    public boolean uiAutoCaseSaveOrUpdate(UiAutoCaseRoiReqDTO autoCaseRoiReqDTO) {
        return handleAutoCaeData(autoCaseRoiReqDTO,PlatformTypeEnum.UI.getCode());
    }

    @Override
    public boolean jmeterAutoCaseSaveOrUpdate(JmeterAutoCaseRoiReqDTO autoCaseRoiReqDTO) {
        return handleAutoCaeData(autoCaseRoiReqDTO,PlatformTypeEnum.JMETER.getCode());
    }


    /**
     * 处理自动用例数据并落库
     * @param autoCaseRoiReqDTO
     * @param platformType
     * @return
     */
    private boolean handleAutoCaeData(UiAutoCaseRoiReqDTO autoCaseRoiReqDTO,Integer platformType) {
        String scenarioId = autoCaseRoiReqDTO.getScenarioId();
        String scenarioName = autoCaseRoiReqDTO.getScenarioName();
        Integer developmentTime = autoCaseRoiReqDTO.getDevelopmentTime();
        Integer maintenanceTime = autoCaseRoiReqDTO.getMaintenanceTime();
        Integer manualTestTime = autoCaseRoiReqDTO.getManualTestTime();
        String author = autoCaseRoiReqDTO.getAuthor();
        String executionUser = autoCaseRoiReqDTO.getExecutionUser();
        String projectId = autoCaseRoiReqDTO.getProjectId();
        User user = userService.getUserByEmail(author);
        String userName = user.getName();

        if (StringUtils.isEmpty(scenarioName)) {
            throw new RuntimeException("scenarioName 不能为空");
        }
        if (StringUtils.isEmpty(scenarioId)) {
            throw new RuntimeException("scenarioId 不能为空");
        }
        if (developmentTime <= 0) {
            throw new RuntimeException("developmentTime 必须大于0");
        }
        if (manualTestTime <= 0) {
            throw new RuntimeException("manualTestTime 必须大于0");
        }
        if (StringUtils.isEmpty(executionUser)) {
            autoCaseRoiReqDTO.setExecutionUser(userName);
            executionUser = userName;
        }

        AutoCaseRoiEntity autoCaseRoi = autoCaseRoiMapper.selectOne(new QueryWrapper<AutoCaseRoiEntity>().eq("scenario_id", scenarioId));

        // ID 不为空表示已经保存过，不需要再保存，则做更新
        if (Objects.nonNull(autoCaseRoi) && !StringUtils.isEmpty(executionUser)) {
            autoCaseRoi.setScenarioName(scenarioName);
            autoCaseRoi.setDevelopmentTime(developmentTime);
            autoCaseRoi.setMaintenanceTime(maintenanceTime);
            autoCaseRoi.setManualTestTime(manualTestTime);

            Integer times = autoCaseRoi.getTimes();
            // 每执行一次 + 1
            times = times + 1;
            // 每次执行一次 * 1。手工测试节省时间累计
            Long saveTimes = autoCaseRoi.getSaveTime() + autoCaseRoi.getManualTestTime();
            autoCaseRoi.setTimes(times);
            autoCaseRoi.setSaveTime(saveTimes);

            autoCaseRoi.setRoi(calculateRoi(autoCaseRoi));
            autoCaseRoi.setExecutionUser(executionUser);
            autoCaseRoi.setAuthor(autoCaseRoiReqDTO.getAuthor());

            autoCaseRoi.setProjectId(projectId);
            autoCaseRoi.setPlatformType(platformType);

            autoCaseRoiMapper.updateById(autoCaseRoi);

        } else {
            // ID 为空表示第一次保存，则做插入
            AutoCaseRoiEntity newAutoCaseRoi = new AutoCaseRoiEntity();
            newAutoCaseRoi.setScenarioId(scenarioId);
            newAutoCaseRoi.setScenarioName(scenarioName);
            newAutoCaseRoi.setDevelopmentTime(developmentTime);
            newAutoCaseRoi.setMaintenanceTime(maintenanceTime);
            newAutoCaseRoi.setManualTestTime(manualTestTime);
            newAutoCaseRoi.setTimes(1);
            newAutoCaseRoi.setSaveTime(Long.valueOf(manualTestTime));
            newAutoCaseRoi.setRoi(calculateRoi(newAutoCaseRoi));

            newAutoCaseRoi.setExecutionUser(executionUser);
            newAutoCaseRoi.setAuthor(author);
            // 只在新增用例的时候才写入创建人，默认写本次写入的用例负责人
            newAutoCaseRoi.setCreator(author);

            newAutoCaseRoi.setProjectId(projectId);
            newAutoCaseRoi.setPlatformType(platformType);

            // 处理通过平台新增的scenario_id场景
            if (Objects.nonNull(autoCaseRoi)) {
                newAutoCaseRoi.setId(autoCaseRoi.getId());
                autoCaseRoiMapper.updateById(newAutoCaseRoi);
            } else {
                autoCaseRoiMapper.insert(newAutoCaseRoi);
            }
            autoCaseRoi = newAutoCaseRoi;
        }

        // 保存到执行记录表（ui和jmeter均使用该方法）
        return autoExecutionRecordService.uiSaveOrUpdate(autoCaseRoi, autoCaseRoiReqDTO);
    }

    /**
     * @return 获取所有测试场景的总节省成本的总和
     */
    @Override
    public long getAllScenarioSaveTime() {
        return 0;
    }

    private String calculateRoi(AutoCaseRoiEntity autoCaseRoi) {
        Long saveTimes = autoCaseRoi.getSaveTime();
        int sumCostTimes = autoCaseRoi.getDevelopmentTime() + autoCaseRoi.getMaintenanceTime();
        BigDecimal roi = new BigDecimal(saveTimes).divide(new BigDecimal(sumCostTimes), 9, RoundingMode.HALF_UP);
        return roi.toString();
    }

}
