package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 自动化用例增长趋势表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Data
@TableName("auto_case_increase_chart")
@Schema(name = "AutoCaseIncreaseChartEntity", description = "自动化用例增长趋势表")
public class AutoCaseIncreaseChartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "新增用例数")
    @TableField("increase_case")
    private Integer increaseCase;

    @Schema(description = "总开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @Schema(description = "总手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @Schema(description = "删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
