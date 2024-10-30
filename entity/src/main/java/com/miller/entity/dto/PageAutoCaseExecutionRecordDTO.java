package com.miller.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author panjuxiang
 * @since 2024/10/10 21:13
 */
@Data
public class PageAutoCaseExecutionRecordDTO {

    @Schema(description = "分页页码")
    private int pageNo = 1;
    @Schema(description = "分页大小")
    private int pageSize = 10;

    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "执行开始时间")
    private Date executionStartTime;

    @Schema(description = "执行结束时间")
    private Date executionEndTime;

    @Schema(description = "执行人员")
    private List<String> executionUserList;

    @Schema(description = "执行策略 1:日常巡检;2:质量保证;3:效率提升")
    private List<String> executionTypeList;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    private List<String> executionStatusList;

    @Schema(description = "排序字段，1：执行日期")
    private Integer sort = 1;

    @Schema(description = "排序规则，0：正序 ，1：逆序")
    private Integer orderBy = 0;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "开发成本符号 1 小于 2 等于 3 大于")
    private Integer developmentTimeSymbol = 0;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "维护成本符号 1 小于 2 等于 3 大于")
    private Integer maintenanceTimeSymbol = 0;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;

    @Schema(description = "手工测试成本符号 1 小于 2 等于 3 大于")
    private Integer manualTestTimeSymbol = 0;

}
