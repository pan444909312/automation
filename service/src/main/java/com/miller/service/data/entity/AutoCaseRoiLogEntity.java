package com.miller.service.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

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
    private Integer saveTime;

    @Schema(description = "场景ROI")
    @TableField("roi")
    private String roi;

    @Schema(description = "执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private Long createTime;

}
