package com.miller.util;

import org.junit.Test;

import java.util.*;

/**
 * @author panjuxiang
 * @since 2024/10/21 16:36
 */
public class CommonUtils {

    @Test
    public void test(){
        String s = "hp-finance-server\tfeature/ID1066843_commission_tax\t业务需求\t【财务服务】EU销项税定制化逻辑\t朱元杰\t朱元杰\t朱元杰\n" +
                "hp-merchant-server\tfeature/ID1066612_diedai\t技术优化\t【B侧】税务信息记录优化\t/\t 王晓皓\t朱元杰\n" +
                "hp-merchant-server\tfeature/ID1062873\t技术优化\t【技术需求】ERP商家商品日志，操作人字段优化\t祝孟康\t陈晓晗\t陈晓晗\n" +
                "platform\tfeature/ID1062873\t技术优化\t【技术需求】ERP商家商品日志，操作人字段优化\t祝孟康\t陈晓晗\t陈晓晗\n" +
                "hp-product-server\tfeature/dockerFile0911\t技术优化\t【B侧】阿里云流水线发布配置文件修改\t/\t 林天成\t 林天成\n" +
                "hp-product-server-job\tfeature/dockerFile0911\t技术优化\t【B侧】阿里云流水线发布配置文件修改\t/\t 林天成\t 林天成\n" +
                "hp-promotion-server-job\tfeature/dockerFile0911\t技术优化\t【B侧】阿里云流水线发布配置文件修改\t/\t 林天成\t 林天成\n" +
                "hp-voucher-server\tfeature/dockerFile0911\t技术优化\t【B侧】阿里云流水线发布配置文件修改\t/\t 林天成\t 林天成\n" +
                "hp-merchant-server\tfeature/ID1064715\t业务需求\t【【B侧】店铺批量出餐配置】\t姚裕辉\t张培\t吕志鹏\n" +
                "hp-erp-server\tfeature/ID1064715\t业务需求\t【【B侧】店铺批量出餐配置】\t姚裕辉\t张培\t吕志鹏\n" +
                "platform\tfeature/ID1064715\t业务需求\t【【B侧】店铺批量出餐配置】\t姚裕辉\t张培\t吕志鹏";
        System.out.println(publishContentGenerator(s));
    }

    public static StringBuilder publishContentGenerator(String sourceValue) {
        // 数据拆分组合
        String[] rows = sourceValue.split("\n");
        Map<String, List<String>> resMap = new HashMap<>();
        for (int i = 0; i < rows.length; i++) {
            List<String> colList = Arrays.asList(rows[i].split("\t"));
            String key = colList.get(0).trim();

            colList = colList.subList(1, colList.size());
            String value = String.valueOf(colList).trim();
            value = value.substring(1, value.length() - 1);

            List<String> valueList = resMap.containsKey(key) ? resMap.get(key) : new ArrayList();
            valueList.add(value);
            resMap.put(key, valueList);
        }

        StringBuilder sb = new StringBuilder();
        // 打印输出
        resMap.forEach((key, values) -> {
            sb.append("【".concat(key).concat("】")).append(System.lineSeparator());
//            System.out.println("【".concat(key).concat("】"));
//            values.forEach(v -> System.out.println("\t".concat(v)));
            values.forEach(v -> sb.append("\r\t").append(v).append(System.lineSeparator()));
        });
        return sb;
    }
}
