package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 自动化用例执行趋势表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Data
@TableName("auto_case_execution_chart")
@Schema(name = "AutoCaseExecutionChart", description = "自动化用例执行趋势表")
public class AutoCaseExecutionChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "执行用例数")
    @TableField("execution_case")
    private Integer executionCase;

    @Schema(description = "执行成功数量")
    @TableField("execution_success_time")
    private Integer executionSuccessTime;

    @Schema(description = "执行失败数量")
    @TableField("execution_fail_time")
    private Integer executionFailTime;

    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

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
