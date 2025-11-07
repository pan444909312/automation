package com.miller.entity.report.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/9/29 11:23
 */
@Data
public class AutoCaseRoiRespDTO {
    @Schema(description = "ID 自增")
    private Long id;

    @Schema(description = "场景id")
    private String scenarioId;

    @Schema(description = "场景名称")
    private String scenarioName;

    @Schema(description = "开发成本")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    private Integer manualTestTime;

    @Schema(description = "执行次数")
    private Integer times;

    @Schema(description = "总节省成本")
    private long saveTime;

    @Schema(description = "场景ROI")
    private String roi;

    @Schema(description = "执行人员名称")
    private String executionUser;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "关联项目id")
    private String projectId;

    @Schema(description = "用例负责人，邮箱地址")
    private String author;

    @Schema(description = "用例创建人")
    private String creator;

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    private Integer platformType;

    @Schema(description = "用例状态 0活跃 1非活跃 2弃用 ")
    @TableField("status")
    private Integer status;

}
