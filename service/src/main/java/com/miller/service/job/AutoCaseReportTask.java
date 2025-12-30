package com.miller.service.job;

import com.miller.entity.report.req.ListDailyResultSummaryReqDTO;
import com.miller.service.report.AutoCaseDailyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: panjuxiang
 * @Since: 2025/12/30
 */

@Component
@Slf4j
public class AutoCaseReportTask {

    @Autowired
    private AutoCaseDailyReportService autoCaseDailyReportService;


    /**
     * 每日 13:30 执行
     */
    @Scheduled(cron = "0 30 13 * * ?")
    public void execute() {

        LocalDate todayLocalDate = LocalDate.now();

        ListDailyResultSummaryReqDTO listDailyResultSummaryReqDTO = new ListDailyResultSummaryReqDTO();

        listDailyResultSummaryReqDTO.setDate(todayLocalDate.toString());

        autoCaseDailyReportService.addAutoCaseDailyReportList(listDailyResultSummaryReqDTO);

    }

}
