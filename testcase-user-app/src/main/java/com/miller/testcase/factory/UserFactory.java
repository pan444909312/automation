package com.miller.testcase.factory;

import com.miller.entity.tools.CodeInfo;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    // 添加辅助方法来安全转换各种类型到String
    private static String safeToString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof LocalDateTime) {
            // 自定义日期格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return ((LocalDateTime) obj).format(formatter);
        }
        return obj.toString();
    }

    public static List<CodeInfo> getCaptchas(String phone) {
        // 加密手机号
        String encryptedPhone = getPhoneNumber(phone);

        // 移除 user_id 字段
        String sql = String.format(
                "SELECT telephone, FROM_UNIXTIME(create_time/1000) as create_time, verifycode FROM user_log WHERE telephone = '%s' OR telephone = '%s' ORDER BY create_time DESC limit 5",
                encryptedPhone, phone);
        List<Map<String, Object>> selectListSql = PandaTestDBHelpful.executeSelectListSql(sql);

        // 创建新的CodeInfo对象列表
        List<CodeInfo> codeInfoList = new ArrayList<>();

        if (!selectListSql.isEmpty()) {
            for (Map<String, Object> row : selectListSql) {
                // 只传递三个参数，不包括 user_id
                CodeInfo codeInfo = new CodeInfo(
                        safeToString(row.get("telephone")),
                        safeToString(row.get("create_time")),
                        safeToString(row.get("verifycode"))
                );
                codeInfoList.add(codeInfo);
            }
        }

        return codeInfoList;
    }

    public static void main(String[] args){
        List<CodeInfo> result = getCaptchas("13711111111");
        System.out.println(result);
    }

}