package com.miller.demo.issuesv2.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.issues.response.IssuesListResponseDTO;
import com.miller.service.framework.http.HttpUtils;

import java.util.Map;

/**
 * 流程_查询缺陷
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 18:22:50
 */
public class IssueQueryFlow {
    /**
     * 查询缺陷接口
     */
    private static final String queryIssueURI = SystemConfigConstant.DOMAIN + "/issues/list/";

    public static IssuesListResponseDTO queryIssue(Map<String, Object> token, String userName, Integer pageNum, Integer pageSize) {
        return HttpUtils.sendGetRequestReturnJavaObject(
                queryIssueURI + userName + "/" + pageNum + "/" + pageSize,
                null, token, null, IssuesListResponseDTO.class);
    }
}
