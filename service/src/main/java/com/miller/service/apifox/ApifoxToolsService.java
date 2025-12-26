package com.miller.service.apifox;


import com.miller.entity.apifox.DTO.ApiFoxToolsExecDTO;
import com.miller.service.apifox.enums.AttributionGroupEnum;
import org.springframework.scheduling.annotation.Async;

public interface ApifoxToolsService {


    @Async
    void scheduledTaskAsync(AttributionGroupEnum attributionGroupEnum);

    void execApifoxCli(AttributionGroupEnum attributionGroupEnum);

    @Async
    void execApifoxCli(String taskId);

    @Async
    void execApifoxCli(ApiFoxToolsExecDTO execDTO);
}
