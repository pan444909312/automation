package com.miller.entity.apifox;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * Apifox 运行结果统计表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("apifox_run_report")
public class ApiFoxRunReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public ApiFoxRunReportEntity() {
    }

    public ApiFoxRunReportEntity(String runId, String belongingGroup, String responsiblePerson, int totalRuns, int successRuns, int failureRuns, String createTime, double successRate, double failureRate) {
        this.runId = runId;
        this.belongingGroup = belongingGroup;
        this.responsiblePerson = responsiblePerson;
        this.totalRuns = totalRuns;
        this.successRuns = successRuns;
        this.failureRuns = failureRuns;
        this.createTime = createTime;
        this.successRate = successRate;
        this.failureRate = failureRate;
    }

    /**
     * 任务唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String runId;

    /**
     * 任务归属小组（如：研发一组、测试部）
     */
    private String belongingGroup;

    /**
     * 任务负责人（姓名或工号）
     */
    private String responsiblePerson;

    /**
     * 任务总运行次数
     */
    private int totalRuns;

    /**
     * 任务成功运行次数
     */
    private int successRuns;

    /**
     * 任务失败运行次数
     */
    private int failureRuns;

    /**
     * 任务创建时间（与运行时间一致，格式：yyyy-MM-dd HH:mm:ss）
     */
    private String createTime;

    /**
     * 任务成功率（公式：successRuns/totalRuns * 100%）
     */
    private double successRate;

    /**
     * 任务失败率（公式：failureRuns/totalRuns * 100%）
     */
    private double failureRate;

    /**
     * 执行步骤总数
     */
    private Integer stepTotal;

    /**
     * 执行失败步骤数量
     */
    private Integer failStepCount;

    /**
     * 执行成功步骤数量
     */
    private Integer passStepCount;


}
