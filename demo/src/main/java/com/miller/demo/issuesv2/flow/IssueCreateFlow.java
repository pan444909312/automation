package com.miller.demo.issuesv2.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;

import java.util.Map;

/**
 * 创建缺陷
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 17:34:44
 */
public class IssueCreateFlow {
    /**
     * 新增缺陷接口
     */
    private static final String createIssueURI = SystemConfigConstant.DOMAIN + "/issues/createIssue/";

    /**
     * 新增缺陷
     *
     * @param token           用户身份
     * @param issueRequestDTO 请求数据
     * @return 新增缺陷响应体
     */
    public static BasicResponseDTO createIssue(Map<String, Object> token, IssueRequestDTO issueRequestDTO) {
        String body = JSONUtils.toJSONString(issueRequestDTO);
        return HttpUtils.sendPostRequestReturnJavaObject(
                createIssueURI, null, token, body, null, BasicResponseDTO.class);
    }

}
