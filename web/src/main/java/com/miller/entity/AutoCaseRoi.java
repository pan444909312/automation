package com.miller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 自动化用例ROI表
 * </p>
 *
 * @author panjuxiang
 * @since 2024-09-19
 */
@Getter
@Setter
@TableName("auto_case_roi")
@ApiModel(value = "AutoCaseRoi对象", description = "自动化用例ROI表")
public class AutoCaseRoi implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID 自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("场景id")
    @TableField("scenario_id")
    private String scenarioId;

    @ApiModelProperty("场景名称")
    @TableField("scenario_name")
    private String scenarioName;

    @ApiModelProperty("开发成本")
    @TableField("development_time")
    private Integer developmentTime;

    @ApiModelProperty("维护成本")
    @TableField("maintenance_time")
    private Integer maintenanceTime;

    @ApiModelProperty("手工测试成本")
    @TableField("manual_test_time")
    private Integer manualTestTime;

    @ApiModelProperty("执行次数")
    @TableField("times")
    private Integer times;

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

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty("删除标记（0:可用 1:不可用）")
    @TableField("is_deleted")
    private Byte isDeleted;
}
