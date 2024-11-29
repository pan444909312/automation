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
 * 测试场景总ROI表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-10-15
 */
@Data
@TableName("auto_case_roi_chart")
@Schema(name = "AutoCaseRoiChartEntity", description = "测试场景总ROI表")
public class AutoCaseRoiChartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "成本")
    @TableField("cost_time")
    private Long costTime;

    @Schema(description = "投产次数")
    @TableField("times")
    private Integer times;

    @Schema(description = "收益")
    @TableField("save_time")
    private Long saveTime;

    @Schema(description = "场景ROI")
    @TableField("roi")
    private String roi;

    @Schema(description = "执行策略 0:所有策略 1:日常巡检;2:质量保证;3:效率提升")
    @TableField("execution_type")
    private Integer executionType;

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
