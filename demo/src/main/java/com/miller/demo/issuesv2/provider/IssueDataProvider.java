package com.miller.demo.issuesv2.provider;

import com.miller.common.util.ULIDUtils;
import com.miller.demo.issues.request.IssueRequestDTO;
import com.miller.demo.login.request.LoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_缺陷
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 20:03:53
 */
@SuppressWarnings("unused")
public class IssueDataProvider {
    static Stream<Arguments> creatIssueDataProvider() {
        IssueRequestDTO issueDTO = new IssueRequestDTO();
        issueDTO.setTitle("测试创建缺陷" + ULIDUtils.generateULID());
        issueDTO.setDescription("这是缺陷描述");
        issueDTO.setHandler("miller.shan@aliyun.com");

        return Stream.of(arguments(issueDTO));
    }
}
