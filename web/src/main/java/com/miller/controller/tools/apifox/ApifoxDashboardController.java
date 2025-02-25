package com.miller.controller.tools.apifox;
import com.miller.pos.date.flow.WorkingTimeFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/apifox/dashboard")
public class ApifoxDashboardController {


    @GetMapping("/getWorkingCount")
    public int getWorkingCount(@RequestParam("startTime") String startTime ,@RequestParam("endTime") String endTime ){
        log.info("开始时间：{},结束时间：{}",startTime,endTime);
        return WorkingTimeFlow.getDays(startTime, endTime);
    }




}
