package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 自动化测试接口覆盖率实体类
 * 用于记录和追踪API接口的自动化测试覆盖情况
 */
@Data
@TableName("automation_coverage_api")
public class AutomationCoverageApiEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 是否已经实现自动化测试,0:未实现、1:已实现
     */
    private Integer isAutomation;

    /**
     * 此接口接口测试对应的负责人
     */
    private String apiTestAuthor;

    /**
     * 最后一次执行自动化测试的时间戳
     */
    private Long lastExecuteTime;
    /**
     * 测试用例执行结果: Successful, Failed, Disabled, Aborted.
     */
    private String lastExecuteResult;
    /**
     * 最后一次执行自动化测试的人员
     */
    private String lastExecutor;

    /**
     * 接口状态:0:正常、-1:已废弃
     */
    private Integer apiStatus;

    /**
     * 测试用例的url的请求参数
     */
    private String testCaseRequestPath;

    /**
     * 测试用例请求Method
     */
    private String testCaseRequestMethod;

    /**
     * 测试用例请求uri
     */
    private String testCaseRequestUri;

    /**
     * 测试用例请求头
     */
    private String testCaseRequestHeaders;

    /**
     * 测试用例请求体
     */
    private String testCaseRequestBody;

    /**
     * 最后一次测试用例的响应结果
     */
    private String testCaseResponseBody;

    /**
     * 最后一次测试用例的响应状态码,通常 200 代表成功
     */
    private String testCaseResponseStatusCode;

    /**
     * 项目ID，冗余字段，暂时提供给外部系统使用
     */
    private String projectId;

}