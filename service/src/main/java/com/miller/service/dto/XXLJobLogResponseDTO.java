package com.miller.service.dto;

import lombok.Data;
import java.util.List;

/**
 * XXL Job 日志查询响应DTO
 * 对应XXL-Job的/joblog/pageList API响应格式
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/12/19
 */
@Data
public class XXLJobLogResponseDTO {
    private List<XXLJobLogItem> data;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private Integer start;
    private Integer length;
    private Integer draw;

    @Data
    public static class XXLJobLogItem {
        private Long id;
        private Long jobGroup;
        private Long jobId;
        private String executorAddress;
        private String executorHandler;
        private String executorParam;
        private String executorShardingParam;
        private Integer executorFailRetryCount;
        private String triggerTime;
        private Integer triggerCode;  // 修改为Integer类型
        private String triggerMsg;
        private String handleTime;
        private Integer handleCode;
        private String handleMsg;
        private Integer alarmStatus;
        private String processId;
        private Long maxId;
    }
}
