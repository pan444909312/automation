package com.miller.service.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 仪表盘数据 VO
 */
@Data
@Accessors(chain = true)
public class DashboardVO {

    private String attributionObject;

    /**
     * 用例总数
     */
    private Integer caseCount;

    /**
     * 总的 ROI
     */
    private Double roi;

    /**
     * 执行次数
     */
    private Integer execCount;

    /**
     * 通过率
     */
    private Long successRate;

    /**
     * 失败率
     */
    private Long failureRate;

    /**
     * 开发成本
     */
    private Double developmentCost;

    /**
     * 维护成本
     */
    private Double maintenanceCost;

    /**
     * 累计节省成本
     */
    private Double cumulativeSavedCost;

    /**
     * 新增用例数
     */
    private Integer newCaseCount;

    /**
     * 活跃用例数
     */
    private Long activeCaseCount;

    /**
     * 沉寂用例数
     */
    private Long inactiveCaseCount;

}
