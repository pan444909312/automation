package com.miller.entity.report.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 最近执行记录响应DTO
 *
 * @author panjuxiang
 */
@Data
public class RecentExecutionRecordRespDTO {

    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "用例负责人")
    private String author;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private Integer executionStatus;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "总节省成本")
    private Long saveTime;

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    private Integer platformType;

    @Schema(description = "关联项目id")
    private String projectId;
}
