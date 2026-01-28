package com.miller.controller.tools.apifox;

import com.miller.controller.tools.ResultVO;
import com.miller.service.apifox.ApiFoxRunReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiFox/report")
public class ApiFoxRunReportController {


    @Autowired
    private ApiFoxRunReportService apiFoxRunReportService;


    @PostMapping("/del")
    public ResultVO delReport(@RequestParam Long id,
                              @RequestParam String remark,
                              @RequestParam String optUser) {
        apiFoxRunReportService.delReport(id, remark, optUser);
       return ResultVO.success("数据订正成功");
    }


}
