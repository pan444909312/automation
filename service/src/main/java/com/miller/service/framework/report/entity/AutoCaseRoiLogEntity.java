package com.miller.service.framework.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 自动化测试执行记录日志，用于测试框架执行记录，与平台的分开
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/11/29 19:06:06
 */
@Data
@TableName("auto_case_roi_log")
public class AutoCaseRoiLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @Schema(description = "总节省成本")
    @TableField("save_time")
    private Long saveTime;

    @Schema(description = "场景ROI")
    @TableField("roi")
    private String roi;

    @Schema(description = "执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Long createTime;

    @Schema(description = "开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    @TableField("maintenance_time")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;

}
