package com.miller.service.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("auto_case_roi_log")
public class AutoCaseRoiLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @ApiModelProperty("总节省成本")
    @TableField("save_time")
    private Integer saveTime;

    @ApiModelProperty("场景ROI")
    @TableField("roi")
    private String roi;

    @ApiModelProperty("执行人员名称")
    @TableField("execution_user")
    private String executionUser;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Long createTime;

}
