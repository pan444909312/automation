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
     * 域名，例如：api.example.com
     */
    private String host;
    
    /**
     * HTTP 请求方法，如：GET、POST、PUT、DELETE等
     */
    private String method;
    
    /**
     * 请求路径，例如：/api/v1/users
     */
    private String path;
    
    /**
     * 线上环境实际请求次数，用于统计接口使用频率
     */
    private Long requestsTimesProduction;
    
    /**
     * 国家/地区标识，用于区分不同地区的API调用
     */
    private String country;
    
    /**
     * 是否已经实现自动化测试,0:未实现、1:已实现
     */
    private Integer isAutomation;

    /**
     * 最后一次执行自动化测试的时间戳
     */
    private Long lastExecuteTime;
    
    /**
     * 执行自动化测试的人员姓名
     */
    private String executor;
    
    /**
     * 软删除标记,0:未删除、1:已删除
     */
    private Integer isDelete;

    /**
     * 测试用例的url参数
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

}