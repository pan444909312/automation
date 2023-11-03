package com.miller.demo.issuesv2.flow;

import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.dto.BasicResponseDTO;
import com.miller.service.framework.http.HttpUtils;

import java.util.Map;

/**
 * 缺陷删除
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 17:44:31
 */
public class IssueDeleteFlow {
    /**
     * 删除缺陷接口
     */
    private static final String deleteIssueURI = SystemConfigConstant.DOMAIN + "/issues/deleteIssue/";

    /**
     * 删除缺陷
     *
     * @param token   用户身份
     * @param issueID 请求数据
     * @return 响应体中的 data 字段即缺陷ID
     */
    public static BasicResponseDTO deleteIssue(Map<String, Object> token, String issueID) {
        return HttpUtils.sendDeleteRequestReturnJavaObject(
                deleteIssueURI + issueID, null, token, null, null, BasicResponseDTO.class);
    }
}
