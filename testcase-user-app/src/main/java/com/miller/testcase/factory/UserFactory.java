package com.miller.testcase.factory;

import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.miller.testcase.utils.TestCaseHelpful.getPhoneNumber;

/**
 * 造数据工厂。提供创造各种用户的能力。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/27 23:03:37
 */
public class UserFactory {

    public static String getCaptchas(String phone) {
        // 加密手机号
        String encryptedPhone = getPhoneNumber(phone);

        String sql = String.format(
                "SELECT user_id, telephone, FROM_UNIXTIME(create_time/1000) as create_time, verifycode FROM user_log WHERE telephone = '%s' OR telephone = '%s' ORDER BY create_time DESC limit 5",
                encryptedPhone, phone);
        List<Map<String, Object>> selectListSql = PandaTestDBHelpful.executeSelectListSql(sql);

        StringBuilder result = new StringBuilder();
        result.append("查询结果：\n");

        if (selectListSql.isEmpty()) {
            result.append("没有找到匹配的记录\n");
        } else {
            for (int i = 0; i < selectListSql.size(); i++) {
                Map<String, Object> row = selectListSql.get(i);
                result.append("=== 第").append(i + 1).append("条记录 ===\n");
                result.append("用户ID: ").append(row.get("user_id")).append("\n");
                result.append("手机号: ").append(row.get("telephone")).append("\n");
                result.append("创建时间: ").append(row.get("create_time")).append("\n");
                result.append("验证码: ").append(row.get("verifycode")).append("\n\n");
            }
        }

        return result.toString();
    }



}