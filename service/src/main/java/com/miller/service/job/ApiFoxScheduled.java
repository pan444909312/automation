package com.miller.service.job;

import com.miller.service.apifox.ApifoxToolsService;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ApiFox 定时任务
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/11/6 10:23:44
 */
@Component
public class ApiFoxScheduled {

    @Autowired
    ApifoxToolsService apifoxToolsService;

    @Scheduled(cron = "0 10 1 * * ?")
    public void scheduledTaskB() {
        this.apifoxToolsService.execApifoxCli(AttributionGroupEnum.B);
    }

    @Scheduled(cron = "0 10 3 * * ?")
    public void scheduledTaskP() {
        this.apifoxToolsService.execApifoxCli(AttributionGroupEnum.P);
    }


//    @Scheduled(cron = "0 10 4 * * ?")
//    public void scheduledTaskD() {
//        this.apifoxToolsService.execApifoxCli(AttributionGroupEnum.D);
//    }


}
