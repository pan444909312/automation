package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 自动化用例ROI表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Data
@TableName("auto_case_roi")
@Schema(name = "AutoCaseRoi对象", description = "自动化用例ROI表")
public class AutoCaseRoiEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @Schema(description = "场景名称")
    @TableField("scenario_name")
    private String scenarioName;

    @Schema(description = "开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @Schema(description = "维护成本")
    @TableField("maintenance_time")
    private Integer maintenanceTime;

    @Schema(description = "手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;

    @Schema(description = "执行次数")
    @TableField("times")
    private Integer times;

    @Schema(description = "总节省成本")
    @TableField("save_time")
    private Long saveTime;

    @Schema(description = "场景ROI")
    @TableField("roi")
    private String roi;

    @Schema(description = "执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @Schema(description = "用例负责人，邮箱地址")
    @TableField("author")
    private String author;

    @Schema(description = "优先级")
    @TableField("priority")
    private Integer priority;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;

    @Schema(description = "关联项目id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "预期测试用例被执行的次数")
    @TableField("expect_times")
    private Integer expectTimes;

    @Schema(description = "备注信息")
    @TableField("remark")
    private String remark;
}