package com.miller.demo.issuesv2.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;

import java.util.Map;

/**
 * 流程_缺陷更新
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 19:45:04
 */
public class IssueUpdateFlow {
    /**
     * 更新缺陷URL
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/issues/updateIssue/";

    /**
     * 更新缺陷字段
     *
     * @param token           用户身份
     * @param issueRequestDTO 请求数据
     * @return 新增缺陷响应体
     */
    public static BasicResponseDTO updateIssue(Map<String, Object> token, IssueRequestDTO issueRequestDTO) {
        String body = JSONUtils.toJSONString(issueRequestDTO);
        return HttpUtils.sendPutRequestReturnJavaObject(
                uri, null, token, body, null, BasicResponseDTO.class);
    }
}
