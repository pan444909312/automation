package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
    private String failRate;

    @Schema(description = "今日执行通过率")
    @TableField("success_rate")
    private String successRate;

    @Schema(description = "关联项目id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "用例归属平台类型（1：JAVA 2：APIFOX 3：JMETER 4：UI自动化）")
    @TableField("platform_type")
    private Byte platformType;

    @Schema(description = "报告统计标记")
    @TableField("report_tag")
    private Integer reportTag;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    private Byte isDeleted;
}
