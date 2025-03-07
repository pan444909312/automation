package com.miller.controller.tools.apifox;

import com.miller.pos.date.flow.WorkingTimeFlow;
import com.miller.service.apifox.ApifoxToolsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/apifox/tools")
public class ApifoxToolsController {

    @Autowired
    private ApifoxToolsService apifoxToolsService;


    @GetMapping("/getWorkingCount")
    public int getWorkingCount(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        log.info("开始时间：{},结束时间：{}", startTime, endTime);
        return WorkingTimeFlow.getDays(startTime, endTime);
    }

    @PostMapping("/pushDingDing")
    public void pushDingDing(@RequestParam String access_token,
                             @RequestParam String timestamp,
                             @RequestParam String sign,
                             @RequestBody String body) {
        apifoxToolsService.sendDingDing(access_token, timestamp, sign, body);
    }


}
