package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 自动化用例每日执行报告表
 * </p>
 *
 * @author panjuxiang
 * @since 2025-12-29
 */
@Getter
@Setter
@TableName("auto_case_daily_report")
@Accessors(chain = true)
@Schema(name = "AutoCaseDailyReport", description = "自动化用例每日执行报告表")
public class AutoCaseDailyReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "运行日期")
    @TableField("run_date")
    private String runDate;

    @Schema(description = "用例负责人")
    @TableField("author")
    private String author;

    @Schema(description = "今日执行条数")
    @TableField("total_count")
    private Integer totalCount;

    @Schema(description = "今日执行成功数")
    @TableField("success_count")
    private Integer successCount;

    @Schema(description = "今日执行失败数")
    @TableField("fail_count")
    private Integer failCount;

    @Schema(description = "今日执行失败率")
    @TableField("fail_rate")
    private double failRate;

    @Schema(description = "今日执行通过率")
    @TableField("success_rate")
    private double successRate;

    @Schema(description = "关联项目id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    @TableField("platform_type")
    private Integer platformType;

    @Schema(description = "报告统计标记")
    @TableField("report_tag")
    private Integer reportTag;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    private Byte isDeleted;
}
