package com.miller.controller.report;

import com.miller.entity.report.req.ListDailyResultSummaryReqDTO;
import com.miller.entity.util.Response;
import com.miller.service.report.AutoCaseDailyReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 自动化用例每日执行报告表 前端控制器
 * </p>
 *
 * @author panjuxiang
 * @since 2025-12-29
 */
@RestController
@RequestMapping("/autoCaseDailyReport")
public class AutoCaseDailyReportController {


    @Autowired
    AutoCaseDailyReportService autoCaseDailyReportService;




    @Operation(description = "测试")
    @PostMapping("/addAutoCaseDailyReportTest")
    public Map<String, Object> addAutoCaseDailyReporTest() {

        ListDailyResultSummaryReqDTO listDailyResultSummaryReqDTO = new ListDailyResultSummaryReqDTO();

//        for (int day = 1; day <= 31; day++) {
//            String dateStr = String.format("2025-10-%02d", day);
//            listDailyResultSummaryReqDTO.setDate(dateStr);
//            autoCaseDailyReportService.addAutoCaseDailyReportList(listDailyResultSummaryReqDTO);
//        }
//
//        for (int day = 1; day <= 30; day++) {
//            String dateStr = String.format("2025-11-%02d", day);
//            listDailyResultSummaryReqDTO.setDate(dateStr);
//            autoCaseDailyReportService.addAutoCaseDailyReportList(listDailyResultSummaryReqDTO);
//        }

        for (int day = 1; day <= 29; day++) {
            String dateStr = String.format("2025-12-%02d", day);
            listDailyResultSummaryReqDTO.setDate(dateStr);
            autoCaseDailyReportService.addAutoCaseDailyReportList(listDailyResultSummaryReqDTO);
        }

        return null;
    }

    @Operation(description = "数据新增")
    @PostMapping("/addAutoCaseDailyReport")
    public Response<Boolean> addAutoCaseDailyReport(@RequestBody ListDailyResultSummaryReqDTO reqDTO) {

        return Response.success(autoCaseDailyReportService.addAutoCaseDailyReportList(reqDTO));
    }

}
