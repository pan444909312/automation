package com.miller.entity.report.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author panjuxiang
 * @since 2024/9/26 9:30
 */
@Data
public class UiAutoCaseRoiReqDTO  {
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
    private Integer saveTime;

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

    @Schema(description = "关联项目id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "预期测试用例被执行的次数")
    @TableField("expect_times")
    private Integer expectTimes;

    @Schema(description = "备注信息")
    @TableField("remark")
    private String remark;

    @Schema(description = "用例创建人")
    @TableField("creator")
    private String creator;

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    @TableField("platform_type")
    private Integer platformType;

    @Schema(description = "执行策略 0:未知策略1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

    @Schema(description = "执行结果 -1:执行异常;1:执行成功;2:执行失败")
    @TableField("execution_status")
    private Integer executionStatus;

}
