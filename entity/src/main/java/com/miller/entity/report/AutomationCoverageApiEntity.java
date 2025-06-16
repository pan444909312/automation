package com.miller.entity.report;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 自动化测试接口覆盖率实体类
 * 用于记录和追踪API接口的自动化测试覆盖情况
 */
@Data
@TableName("automation_coverage_api")
@Accessors(chain = true)
public class AutomationCoverageApiEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("host")
    private String host;

    @TableField("method")
    private String method;

    @TableField("path")
    private String path;

    @TableField("requests_times_production")
    private Long requestsTimesProduction;

    @TableField("country")
    private String country;

    @TableField("is_automation")
    private Integer isAutomation;

    @TableField("api_test_author")
    private String apiTestAuthor;

    @TableField("last_execute_result")
    private String lastExecuteResult;

    @TableField("last_execute_time")
    private Long lastExecuteTime;

    @TableField("last_executor")
    private String lastExecutor;

    @TableField("api_status")
    private Integer apiStatus;

    @TableField("test_case_response_status_code")
    private String testCaseResponseStatusCode;

    @TableField("test_case_response_body")
    private String testCaseResponseBody;

    @TableField("test_case_request_path")
    private String testCaseRequestPath;

    @TableField("test_case_request_method")
    private String testCaseRequestMethod;

    @TableField("test_case_request_uri")
    private String testCaseRequestUri;

    @TableField("test_case_request_headers")
    private String testCaseRequestHeaders;

    @TableField("test_case_request_body")
    private String testCaseRequestBody;

    @TableField("project_id")
    private String projectId;

}