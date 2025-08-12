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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入原始手机号：");
        String phone = scanner.nextLine();

        // 加密手机号
        String encryptedPhone = getPhoneNumber(phone);
        System.out.println("加密后的手机号: " + encryptedPhone);

        scanner.close();

        String sql = String.format(
                "SELECT user_id, telephone, FROM_UNIXTIME(create_time/1000) as create_time, verifycode FROM user_log WHERE telephone = '%s' OR telephone = '%s' ORDER BY create_time DESC limit 5",
                encryptedPhone, phone);
        List<Map<String, Object>> selectListSql = PandaTestDBHelpful.executeSelectListSql(sql);
        System.out.println("查询结果：");
        if (selectListSql.isEmpty()) {
            System.out.println("没有找到匹配的记录");
        } else {
            for (int i = 0; i < selectListSql.size(); i++) {
                Map<String, Object> row = selectListSql.get(i);
                System.out.println("=== 第" + (i + 1) + "条记录 ===");
                System.out.println("用户ID: " + row.get("user_id"));
                System.out.println("手机号: " + row.get("telephone"));
                System.out.println("创建时间: " + row.get("create_time"));
                System.out.println("验证码: " + row.get("verifycode"));
                System.out.println();
            }
        }
    }



}