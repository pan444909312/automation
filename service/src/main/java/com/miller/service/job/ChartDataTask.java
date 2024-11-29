package com.miller.service.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.entity.report.*;
import com.miller.service.report.*;
import com.miller.service.util.TimestampUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图表数据定时任务
 *
 * @author panjuxiang
 * @since 2024/11/5 10:21
 */
@Component
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

    @Test
    public void test() {
        System.out.println(TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis()));
    }

    /**
     * 每日0:30分执行一次
     *
     */
    @Scheduled(cron = "30 0 * * *")
    public void execute() {
        // 统计昨日数据
        // 昨天0：00
        long yesterdayStart = TimestampUtils.timestampToYesterdayMidnight(System.currentTimeMillis());
        // 今日0：00
        long yesterdayEnd = yesterdayStart + 60 * 60 * 24 * 1000;

        // 新增 自动化用例执行趋势表 数据
        AutoCaseExecutionChartEntity autoCaseExecutionChartEntity = new AutoCaseExecutionChartEntity();

        QueryWrapper<AutoExecutionRecordEntity> autoExecutionRecordQueryWrapper = new QueryWrapper<>();
        autoExecutionRecordQueryWrapper.ge("execution_time", yesterdayStart);
        autoExecutionRecordQueryWrapper.lt("execution_time", yesterdayEnd);

        List<AutoExecutionRecordEntity> autoExecutionRecordList = autoExecutionRecordService.list(autoExecutionRecordQueryWrapper);
        List<AutoExecutionRecordEntity> successList = autoExecutionRecordList.stream().filter(item -> item.getExecutionStatus() == 1).toList();

        autoCaseExecutionChartEntity.setExecutionCase(autoExecutionRecordList.size());
        autoCaseExecutionChartEntity.setExecutionSuccessTime(successList.size());
        autoCaseExecutionChartEntity.setExecutionFailTime(autoExecutionRecordList.size() - successList.size());

        autoCaseExecutionChartService.save(autoCaseExecutionChartEntity);


        // 新增 自动化用例增长趋势表 数据
        AutoCaseIncreaseChartEntity autoCaseIncreaseChartEntity = new AutoCaseIncreaseChartEntity();

        QueryWrapper<AutoCaseRoiEntity> autoCaseRoiQueryWrapper = new QueryWrapper<>();
        autoCaseRoiQueryWrapper.ge("create_time",yesterdayStart);
        autoCaseRoiQueryWrapper.lt("create_time",yesterdayEnd);

        List<AutoCaseRoiEntity> autoCaseRoiEntityList = autoCaseRoiService.list(autoCaseRoiQueryWrapper);

        autoCaseIncreaseChartEntity.setIncreaseCase(autoCaseRoiEntityList.size());

        autoCaseIncreaseChartService.save(autoCaseIncreaseChartEntity);


        // 新增 测试场景总ROI表 数据 todo
        AutoCaseRoiChartEntity autoCaseRoiChartEntity = new AutoCaseRoiChartEntity();

        autoCaseRoiChartEntity.setCostTime(1L);
        autoCaseRoiChartEntity.setTimes(1);
        autoCaseRoiChartEntity.setSaveTime(1L);
        autoCaseRoiChartEntity.setExecutionType(1);




        // 新增 自动化用例未来数据表 数据 todo
        // chart_type = 1 的数据需要根据执行类型3种新增3条(有几个类型增加几条)，不然roi报表统计数据会有问题


    }

    public void futureDataCalculate(){

    }


}
