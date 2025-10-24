package com.miller.entity.apifox;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * apifox 运行失败结果明细
 */

@Data
@Accessors(chain = true)
@TableName("apifox_run_error_scene")
public class ApiFoxRunErrorSceneEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运行记录唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的报告ID
     */
    private Long reportId;

    /**
     * 场景ID
     */
    private String scenarioId;

    /**
     * 场景名称
     */
    private String scenarioName;

    /**
     * 运行结果（ERROR, SUCCESS）
     */
    private RunResult runResult;

    /**
     * 负责人
     */
    private String responsiblePerson;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 失败步骤明细，jsonArray 格式
     */
    private String stepErrorInfo = "[]";

    /**
     * 外部链接：apifox 场景地址
     */
    private String apifoxUrl;

    /**
     * 运行结果枚举
     */
    public enum RunResult {
        ERROR, SUCCESS
    }

}
