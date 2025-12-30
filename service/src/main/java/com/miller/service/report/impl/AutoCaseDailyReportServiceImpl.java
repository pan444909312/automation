package com.miller.service.report.impl;

import com.miller.entity.constant.ProjectTypeEnum;
import com.miller.entity.report.AutoCaseDailyReport;
import com.miller.entity.report.req.ListDailyResultSummaryReqDTO;
import com.miller.entity.report.resp.AutoCaseExecutionDailyDataDTO;
import com.miller.mapper.report.AutoCaseDailyReportMapper;
import com.miller.service.report.AutoCaseDailyReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miller.service.report.AutoExecutionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 自动化用例每日执行报告表 服务实现类
 * </p>
 *
 * @author panjuxiang
 * @since 2025-12-29
 */
@Service
public class AutoCaseDailyReportServiceImpl extends ServiceImpl<AutoCaseDailyReportMapper, AutoCaseDailyReport> implements AutoCaseDailyReportService {

    @Autowired
    AutoExecutionRecordService autoExecutionRecordService;

    @Autowired
    AutoCaseDailyReportMapper autoCaseDailyReportMapper;


    @Override
    public boolean addAutoCaseDailyReportList(ListDailyResultSummaryReqDTO reqDTO) {

        String projectId = reqDTO.getProjectId();
        String date = reqDTO.getDate();
        LocalDate todayLocalDate;

        if (date != null && !date.isEmpty()) {
            todayLocalDate = LocalDate.parse(reqDTO.getDate());
        } else {
            todayLocalDate = LocalDate.now();
        }

        // 校验projectId在枚举内
        if (projectId != null && !projectId.isEmpty()) {
            projectId = ProjectTypeEnum.getValueByKey(Integer.parseInt(projectId));
        }

        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        AutoCaseDailyReport autoCaseDailyReport;
        List<AutoCaseDailyReport> autoCaseDailyReportList = new ArrayList<>();
        List<AutoCaseExecutionDailyDataDTO> dailyCaseExecutionSummaryByPerson =
                autoExecutionRecordService.getDailyCaseExecutionSummaryByPerson(projectId, today);

        for (AutoCaseExecutionDailyDataDTO caseExecutionSummaryByPerson : dailyCaseExecutionSummaryByPerson) {
            // 初始化entity
            autoCaseDailyReport = new AutoCaseDailyReport();

            autoCaseDailyReport.setRunDate(date);
            autoCaseDailyReport.setAuthor(caseExecutionSummaryByPerson.getAuthor());
            autoCaseDailyReport.setTotalCount(caseExecutionSummaryByPerson.getCount());
            autoCaseDailyReport.setSuccessCount(caseExecutionSummaryByPerson.getSuccessCount());
            autoCaseDailyReport.setFailCount(caseExecutionSummaryByPerson.getFailCount());
            autoCaseDailyReport.setSuccessRate(caseExecutionSummaryByPerson.getPassRate());
            autoCaseDailyReport.setFailRate(1 - caseExecutionSummaryByPerson.getPassRate());
            autoCaseDailyReport.setProjectId(String.valueOf(caseExecutionSummaryByPerson.getProjectId()));


            // 统计标记默认设置0
            autoCaseDailyReport.setReportTag(0);


            // 将数据存入列表
            autoCaseDailyReportList.add(autoCaseDailyReport);
        }

        this.saveBatch(autoCaseDailyReportList);


        return false;
    }
}
