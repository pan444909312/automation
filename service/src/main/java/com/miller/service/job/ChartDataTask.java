package com.miller.service.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.constant.ProjectTypeEnum;
import com.miller.entity.constant.SysConfigConstants;
import com.miller.entity.report.*;
import com.miller.entity.report.resp.AutoCaseExecutionRecordRespDTO;
import com.miller.service.report.*;
import com.miller.common.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 图表数据定时任务
 *
 * @author panjuxiang
 * @since 2024/11/5 10:21
 */
@Component
@Slf4j
public class ChartDataTask {

    @Autowired
    AutoCaseChartFutureDataService autoCaseChartFutureDataService;

    @Autowired
    AutoCaseIncreaseChartService autoCaseIncreaseChartService;

    @Autowired
    AutoCaseExecutionChartService autoCaseExecutionChartService;

    @Autowired
    AutoCaseRoiChartService autoCaseRoiChartService;

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Autowired
    AutoCaseRoiService autoCaseRoiService;

    @Autowired
    ConfigService configService;


    /**
     * todo 初始化各报表数据
     * 需要额外初始化 roi报表的数据
     */
    public void initChartData() {
        // 初始化用例增长趋势表数据 未来数据替换用
        AutoCaseIncreaseChartEntity increaseChartFutureData = autoCaseIncreaseChartService.getOne(new LambdaQueryWrapper<AutoCaseIncreaseChartEntity>().eq(AutoCaseIncreaseChartEntity::getIncreaseCase, -1));
        if (increaseChartFutureData == null) {
            AutoCaseIncreaseChartEntity autoCaseIncreaseChartEntity = new AutoCaseIncreaseChartEntity();
            autoCaseIncreaseChartEntity.setChartDate("2099/01/01");
            autoCaseIncreaseChartEntity.setRemarks("未来数据占位");
            autoCaseIncreaseChartService.save(autoCaseIncreaseChartEntity);
        }

        // 初始化用例执行趋势表数据 未来数据替换用
        AutoCaseExecutionChartEntity executionChartFutureData = autoCaseExecutionChartService.getOne(new LambdaQueryWrapper<AutoCaseExecutionChartEntity>().eq(AutoCaseExecutionChartEntity::getExecutionCase, -1));
        if (executionChartFutureData == null) {
            AutoCaseExecutionChartEntity autoCaseExecutionChartEntity;
            for (ExecutionTypeEnum item : ExecutionTypeEnum.values()) {
                autoCaseExecutionChartEntity = new AutoCaseExecutionChartEntity();
                autoCaseExecutionChartEntity.setRemarks("未来数据占位");
                autoCaseExecutionChartEntity.setChartDate("2099/01/01");
                autoCaseExecutionChartEntity.setExecutionType(item.getCode());
                autoCaseExecutionChartService.save(autoCaseExecutionChartEntity);
            }
        }

        // 初始化测试场景总ROI表数据 未来数据替换用
        AutoCaseRoiChartEntity roiFutureData = autoCaseRoiChartService.getOne(new LambdaQueryWrapper<AutoCaseRoiChartEntity>().eq(AutoCaseRoiChartEntity::getChartDate, "2099/01/01"));
        if (roiFutureData == null) {
            AutoCaseRoiChartEntity autoCaseRoiChartEntity;
            for (ExecutionTypeEnum item : ExecutionTypeEnum.values()) {
                autoCaseRoiChartEntity = new AutoCaseRoiChartEntity();
                autoCaseRoiChartEntity.setChartDate("2099/01/01");
                autoCaseRoiChartEntity.setExecutionType(item.getCode());
                autoCaseRoiChartService.save(autoCaseRoiChartEntity);
            }
        }

    }

    /**
     * 每日0:30分执行一次
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void execute() {

        //更新auto_case_roi表 project_id数据
        updateProjectId();

        // 统计昨日数据
        // 昨天0：00
        long yesterdayStart = TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis());
        // 今日0：00
        long yesterdayEnd = yesterdayStart + 60 * 60 * 24 * 1000;

        QueryWrapper<AutoCaseRoiEntity> autoCaseRoiQueryWrapper;
        QueryWrapper<AutoExecutionRecordEntity> autoExecutionRecordQueryWrapper;

        // 遍历不同项目的数据，以项目维度记录报表数据
        for (ProjectTypeEnum project : ProjectTypeEnum.values()) {

            autoCaseRoiQueryWrapper = new QueryWrapper<>();
            autoCaseRoiQueryWrapper.ge("create_time", yesterdayStart);
            autoCaseRoiQueryWrapper.lt("create_time", yesterdayEnd);
            // 新增 自动化用例增长趋势表 数据
            if (!autoCaseIncreaseChartService.checkTodayHasData(project.getProjectId())) {
                addAutoCaseIncreaseChartData(autoCaseRoiQueryWrapper, project.getProjectId());
            }
            // 用例增长趋势表 未来数据计算
            autoCaseIncreaseFutureDataCalculate(project.getProjectId());

            // chart_type = 1和3 的数据需要根据执行类型4种新增4条(有几个类型增加几条)
            for (ExecutionTypeEnum item : ExecutionTypeEnum.values()) {
                // 构造自动化执行记录表的查询条件 设置昨天一天的时间
                autoExecutionRecordQueryWrapper = new QueryWrapper<>();
                autoExecutionRecordQueryWrapper.ge("execution_time", yesterdayStart);
                autoExecutionRecordQueryWrapper.lt("execution_time", yesterdayEnd);

                // 根据不同的执行策略 新增测试场景总ROI表 数据
                if (!autoCaseRoiChartService.checkTodayHasData(item.getCode(), project.getProjectId())) {
                    addAutoCaseRoiChartData(yesterdayStart, yesterdayEnd, item.getCode(), project.getProjectId());
                }
                // 根据不同的执行策略 新增自动化用例执行趋势表 数据
                if (!autoCaseExecutionChartService.checkTodayHasData(item.getCode(), project.getProjectId())) {
                    addAutoCaseExecutionChartData(yesterdayStart, yesterdayEnd, item.getCode(), project.getProjectId());
                }

                // 新增自动化用例未来数据表 chart_type = 1 的数据
                roiFutureDataCalculate(item.getCode());

                // 用例执行趋势表 未来数据计算
                autoCaseExecutionFutureDataCalculate(item.getCode());
            }

        }


    }

    /**
     * 更新auto_case_roi表 project_id数据
     *
     * @return
     */
    private boolean updateProjectId() {
        List<AutoCaseRoiEntity> autoCaseRoiEntities = autoCaseRoiService.selectAutoCaseRoiProjectId();
        log.info("更新的数量{}", autoCaseRoiEntities.size());
        return autoCaseRoiService.updateBatchById(autoCaseRoiEntities);
    }

    private void autoCaseIncreaseFutureDataCalculate(String projectId) {
        AutoCaseChartFutureDataEntity autoCaseChartFutureDataIncreaseEntity = new AutoCaseChartFutureDataEntity();
        autoCaseChartFutureDataIncreaseEntity.setFutureTime(TimestampUtils.getMidnightTimestampPlusMonths(Integer.parseInt(configService.getConfigByKey(SysConfigConstants.DEFAULT_FUTURE_DATE))));
        autoCaseChartFutureDataIncreaseEntity.setChartType(2);
        autoCaseChartFutureDataIncreaseEntity.setExecutionType(-1);
        autoCaseChartFutureDataIncreaseEntity.setProjectId(projectId);
        autoCaseChartFutureDataIncreaseEntity.setExpectedIncreaseCase(autoCaseIncreaseChartService.getMonthsAverageIncreaseCase(3));

        autoCaseChartFutureDataService.save(autoCaseChartFutureDataIncreaseEntity);
    }

    /**
     * 根据不同执行策略，保存未来数据表中 自动化执行趋势表 的未来数据
     *
     * @param executionType 执行策略
     */
    private void autoCaseExecutionFutureDataCalculate(int executionType) {
        // 用例执行趋势表 未来数据计算
        AutoCaseChartFutureDataEntity autoCaseChartFutureDataExecutionEntity = new AutoCaseChartFutureDataEntity();
        autoCaseChartFutureDataExecutionEntity.setFutureTime(TimestampUtils.getMidnightTimestampPlusMonths(Integer.parseInt(configService.getConfigByKey(SysConfigConstants.DEFAULT_FUTURE_DATE))));
        autoCaseChartFutureDataExecutionEntity.setChartType(3);
        autoCaseChartFutureDataExecutionEntity.setExecutionType(executionType);
        autoCaseChartFutureDataExecutionEntity.setExpectedExecutionCase(autoCaseExecutionChartService.getMonthsAverageExecutionCase(3, executionType));
        autoCaseChartFutureDataService.save(autoCaseChartFutureDataExecutionEntity);

    }

    /**
     * 根据不同执行策略，保存未来数据表中 场景总ROI表 的未来数据
     * 未来累计收益(E)= （当前日期的前3个月日期的累计值 / 3个月的累计次数） * F(当前日期往前6个月的那一天累计收益值 / 当前日期往前3个月的那一天累计收益值，当前默认设置为1.0) + 系统最后一次累计收益值
     *
     * @param executionType 执行策略
     */
    private void roiFutureDataCalculate(int executionType) {

        // 获取当前日期往前3个月那一天的roi报表对象
        AutoCaseRoiChartEntity autoCaseRoiChartEntityThreeMonthsAgo = autoCaseRoiChartService.getOne(
                new QueryWrapper<AutoCaseRoiChartEntity>()
                        .ge("create_time", TimestampUtils.getMidnightTimestampPlusMonths(-3))
                        .lt("create_time", TimestampUtils.getMidnightTimestampPlusMonths(-3) + 1000 * 60 * 60 * 24)
                        .last("limit 1"));

        // 获取当前日期往前6个月那一天的roi报表对象
        AutoCaseRoiChartEntity autoCaseRoiChartEntitySixMonthsAgo = autoCaseRoiChartService.getOne(
                new QueryWrapper<AutoCaseRoiChartEntity>()
                        .ge("create_time", TimestampUtils.getMidnightTimestampPlusMonths(-6))
                        .lt("create_time", TimestampUtils.getMidnightTimestampPlusMonths(-6) + 1000 * 60 * 60 * 24)
                        .last("limit 1"));


        // 当前日期的前3个月日期的累计值
        long slotSaveTime = autoCaseRoiChartService.getTotalSaveTime(executionType);
        // 当前日期的前3个月的累计次数
        int slotTimes = autoCaseRoiChartService.getTotalTimes(executionType);

        // 当前日期往前3个月的那一天累计收益值
        long thirdMonthAgoSaveTime = 0;
        if (autoCaseRoiChartEntityThreeMonthsAgo != null) {
            thirdMonthAgoSaveTime = autoCaseRoiChartEntityThreeMonthsAgo.getSaveTime();
            slotSaveTime = slotSaveTime - thirdMonthAgoSaveTime;
            slotTimes = slotTimes - autoCaseRoiChartEntityThreeMonthsAgo.getTimes();
        }
        // 当前日期往前6个月的那一天累计收益值
        long sixthMonthAgoSaveTime = 0;
        if (autoCaseRoiChartEntitySixMonthsAgo != null) {
            sixthMonthAgoSaveTime = autoCaseRoiChartEntitySixMonthsAgo.getSaveTime();
        }

        AutoCaseChartFutureDataEntity autoCaseChartFutureDataEntity = new AutoCaseChartFutureDataEntity();


        autoCaseChartFutureDataEntity.setFutureTime(TimestampUtils.getMidnightTimestampPlusMonths(Integer.parseInt(configService.getConfigByKey(SysConfigConstants.DEFAULT_FUTURE_DATE))));
        autoCaseChartFutureDataEntity.setChartType(1);
        autoCaseChartFutureDataEntity.setExecutionType(executionType);

        double avgSaveTime = 0;
        if (slotTimes != 0) {
            avgSaveTime = (double) slotSaveTime / slotTimes;
        }

        double dynamicF = 1.0;
        // F(当前日期往前6个月的那一天累计收益值 / 当前日期往前3个月的那一天累计收益值，当前默认设置为1.0)
//        if (thirdMonthAgoSaveTime != 0) {
//            dynamicF = (double) sixthMonthAgoSaveTime / thirdMonthAgoSaveTime == 0 ? 1:(double) sixthMonthAgoSaveTime / thirdMonthAgoSaveTime;
//        }

        double expectedSaveTime = avgSaveTime * dynamicF + autoCaseRoiChartService.getTotalSaveTime(executionType);
        log.info("executionType=" + executionType + ",预计累计收益 ：" + expectedSaveTime);

        autoCaseChartFutureDataEntity.setExpectedSaveTime(Math.round(expectedSaveTime));
        autoCaseChartFutureDataService.save(autoCaseChartFutureDataEntity);

    }

    /**
     * 根据不同执行策略，保存测试场景总ROI表数据
     *
     * @param executionType 执行策略
     */
    private boolean addAutoCaseRoiChartData(long startTime, long endTime, int executionType, String projectId) {

//        queryWrapper.eq("execution_type", executionType);
//        queryWrapper.eq("project_id", projectId);
//        List<AutoExecutionRecordEntity> autoExecutionRecordList = autoExecutionRecordService.list(queryWrapper);

        // 获取昨日指定执行类型和项目的 执行记录
        List<AutoCaseExecutionRecordRespDTO> autoExecutionRecordList = autoExecutionRecordService.listAutoExecutionRecordWithProjectId(startTime, endTime, executionType, projectId);

        long totalMaintenanceTimeLatest = autoCaseRoiChartService.getTotalMaintenanceTime(executionType, projectId);
        long totalDevelopTimeLatest = autoCaseRoiChartService.getTotalDevelopTime(executionType, projectId);
        int totalTimesLatest = autoCaseRoiChartService.getTotalTimes(executionType, projectId);
        long totalSaveTimeLatest = autoCaseRoiChartService.getTotalSaveTime(executionType, projectId);
        double roi = 0;

        if (autoExecutionRecordList.isEmpty()) {
            //todo 该情况需不需要插表？？待确认
            log.info("昨日[auto_execution_record]表没有[execution_type=" + executionType + "]的执行记录");
            if (totalMaintenanceTimeLatest + totalDevelopTimeLatest != 0) {
                roi = (double) totalSaveTimeLatest / (totalMaintenanceTimeLatest + totalDevelopTimeLatest);
            }

            return autoCaseRoiChartService.save(
                    new AutoCaseRoiChartEntity(
                            totalMaintenanceTimeLatest,
                            totalDevelopTimeLatest,
                            totalTimesLatest,
                            totalSaveTimeLatest,
                            roi,
                            executionType,
                            TimestampUtils.timestampToDateStr(System.currentTimeMillis()),
                            projectId
                    )
            );
        }

        long saveTime = 0;
        long developTime = 0;
        long maintenanceTime = 0;

        for (AutoCaseExecutionRecordRespDTO autoCaseExecutionRecordRespDTO : autoExecutionRecordList) {
            developTime = developTime + autoCaseExecutionRecordRespDTO.getDevelopmentTime();
            maintenanceTime = maintenanceTime + autoCaseExecutionRecordRespDTO.getMaintenanceTime();
            saveTime = saveTime + autoCaseExecutionRecordRespDTO.getManualTestTime();
        }


        // 累计维护成本计算，累计昨日的maintenance_time 总和 + 当前记录的累计维护成本
        long totalMaintenanceTime = maintenanceTime + totalMaintenanceTimeLatest;
        // 累计开发成本计算，累计昨日的development_time 总和 + 当前记录的累计开发成本
        long totalDevelopTime = developTime + totalDevelopTimeLatest;
        // 累计执行次数计算，累计昨日的执行次数总和 + 当前记录的累计执行次数
        int totalTimes = autoExecutionRecordList.size() + totalTimesLatest;
        // 累计收益计算，累计昨日的manual_test_time 总和 + 当前记录的累计收益
        long totalSaveTime = saveTime + totalSaveTimeLatest;

        // 收益(ROI) = 累计收益 / （累计开发成本 + 累计维护成本）
        if (totalMaintenanceTime + totalDevelopTime != 0) {
            roi = (double) totalSaveTime / (totalMaintenanceTime + totalDevelopTime);
        }

        return autoCaseRoiChartService.save(new AutoCaseRoiChartEntity(totalMaintenanceTime, totalDevelopTime, totalTimes, totalSaveTime, roi, executionType, TimestampUtils.timestampToDateStr(System.currentTimeMillis()), projectId));

    }


    /**
     * 根据不同执行策略，保存自动化用例执行趋势表数据
     *
     * @param executionType 执行策略
     * @return
     */
    private boolean addAutoCaseExecutionChartData(long startTime, long endTime, int executionType, String projectId) {

//        queryWrapper.eq("execution_type", executionType);
//        List<AutoExecutionRecordEntity> autoExecutionRecordList = autoExecutionRecordService.list(queryWrapper);

        AutoCaseExecutionChartEntity autoCaseExecutionChartEntity = new AutoCaseExecutionChartEntity();
        autoCaseExecutionChartEntity.setExecutionType(executionType);
        autoCaseExecutionChartEntity.setProjectId(projectId);

        List<AutoCaseExecutionRecordRespDTO> autoExecutionRecordList = autoExecutionRecordService.listAutoExecutionRecordWithProjectId(startTime, endTime, executionType, projectId);


        if (autoExecutionRecordList.isEmpty()) {
            autoCaseExecutionChartEntity.setChartDate(TimestampUtils.timestampToDateStr(System.currentTimeMillis()));
            return autoCaseExecutionChartService.save(autoCaseExecutionChartEntity);
        }
        List<AutoCaseExecutionRecordRespDTO> successList = autoExecutionRecordList.stream().filter(item -> item.getExecutionStatus() == 1).toList();

        autoCaseExecutionChartEntity.setExecutionCase(autoExecutionRecordList.size());
        autoCaseExecutionChartEntity.setExecutionSuccessTime(successList.size());
        autoCaseExecutionChartEntity.setExecutionFailTime(autoExecutionRecordList.size() - successList.size());
        autoCaseExecutionChartEntity.setChartDate(TimestampUtils.timestampToDateStr(System.currentTimeMillis()));

        return autoCaseExecutionChartService.save(autoCaseExecutionChartEntity);
    }

    /**
     * 根据不同项目id，保存自动化用例新增趋势表数据
     *
     * @param autoCaseRoiQueryWrapper
     * @param projectId               项目id
     * @return
     */
    private boolean addAutoCaseIncreaseChartData(QueryWrapper<AutoCaseRoiEntity> autoCaseRoiQueryWrapper, String projectId) {

        autoCaseRoiQueryWrapper.eq("project_id", projectId);

        AutoCaseIncreaseChartEntity autoCaseIncreaseChartEntity = new AutoCaseIncreaseChartEntity();

        List<AutoCaseRoiEntity> autoCaseRoiEntityList = autoCaseRoiService.list(autoCaseRoiQueryWrapper);
        int increaseCaseDevelopmentTime = 0;
        int increaseCaseManualTestTime = 0;
        log.info("项目id：" + projectId + " -- autoCaseRoiEntityList={}", autoCaseRoiEntityList);
        if (autoCaseRoiEntityList != null && !autoCaseRoiEntityList.isEmpty()) {
            for (AutoCaseRoiEntity autoCaseRoiEntity : autoCaseRoiEntityList) {
                increaseCaseDevelopmentTime = increaseCaseDevelopmentTime + autoCaseRoiEntity.getDevelopmentTime();
                increaseCaseManualTestTime = increaseCaseManualTestTime + autoCaseRoiEntity.getManualTestTime();
            }
            autoCaseIncreaseChartEntity.setIncreaseCase(autoCaseRoiEntityList.size());
            autoCaseIncreaseChartEntity.setDevelopmentTime(increaseCaseDevelopmentTime);
            autoCaseIncreaseChartEntity.setManualTestTime(increaseCaseManualTestTime);
        }
        autoCaseIncreaseChartEntity.setProjectId(projectId);
        autoCaseIncreaseChartEntity.setChartDate(TimestampUtils.timestampToDateStr(System.currentTimeMillis()));
        return autoCaseIncreaseChartService.save(autoCaseIncreaseChartEntity);

    }

}
