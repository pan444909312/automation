package com.miller.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>
 * 自动化用例ROI表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Data
@Accessors(chain = true)
@TableName("auto_case_roi_log")
@Schema(name = "AutoCaseRoi对象", description = "自动化用例ROI日志表")
public class AutoCaseRoiLog implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @Schema(description = "开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    @TableField("maintenance_time")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;


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
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;


}