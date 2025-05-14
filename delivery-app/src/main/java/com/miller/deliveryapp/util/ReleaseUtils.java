package com.miller.deliveryapp.util;

import java.util.*;

public class ReleaseUtils {
    public static void main(String[] args) {
        //  发布服务元数据
        String sourceValue = "delivery-server\tfeature/ID1078296\t技术优化\t【D侧】管控-部分骑手时，计算异常\t/\t陈春霞\t石成明\n" +
                "delivery-server-job\tfeature/ID1078296\t技术优化\t【D侧】管控-部分骑手时，计算异常\t/\t陈春霞\t石成明\n" +
                "base-common\tfeature/ID1076556\t业务需求\t【D侧】调度系统运力系数和尾单需求\t李绍奕\t陈春霞\t郭书运\n" +
                "f2e-dispatch-control\tfeature/1076556_capacity_wyy\t业务需求\t【D侧】调度系统运力系数和尾单需求\t李绍奕\t陈春霞\t武银银\n" +
                "dispatch-server\tfeature/ID1076556\t业务需求\t【D侧】调度系统运力系数和尾单需求\t李绍奕\t陈春霞\t郭书运\n" +
                "hp-order-server\tfeature/ID1076556\tBug\t【线上Bug】客服查询模块 ，老订单详情，商家接单时间 没有进行时区转化\t无\t彭路路\t郑彤\n" +
                "hp-order-server\tfeature/ID1076556\t业务需求\t【D侧】调度系统运力系数和尾单需求\t李绍奕\t陈春霞\t郭书运\n" +
                "delivery-server\tfeature/ID1076556\t业务需求\t【D侧】调度系统运力系数和尾单需求\t李绍奕\t陈春霞\t郭书运\n" +
                "f2e-panda-delivery-manage\tfeature/1076559_optCap_wyy\t业务需求\t【D侧】运力告警喊人优化\t李绍奕\t陈春霞\t武银银\n" +
                "delivery-server\tfeature/ID1076559\t业务需求\t【D侧】运力告警喊人优化\t李绍奕\t陈春霞\t郭书运";

        // 数据拆分组合
        String[] rows = sourceValue.split("\n");
        Map<String, List<String>> resMap = new HashMap<>();
        for (int i = 0; i < rows.length; i++) {
            List<String> colList = Arrays.asList(rows[i].split("\t"));
            String key = colList.get(0).trim();

            colList = colList.subList(1,colList.size());
            String value = String.valueOf(colList).trim();
            value = value.substring(1,value.length()-1);

            List<String> valueList = resMap.containsKey(key)? resMap.get(key) : new ArrayList();
            valueList.add(value);
            resMap.put(key,valueList);
        }

        // 打印输出
        resMap.forEach((key,values) ->{
            System.out.println("【".concat(key).concat("】"));
            for (int i = 0; i < values.size(); i++) {
                System.out.println("\t".concat(String.valueOf(i+1)).concat(". ").concat(values.get(i)));
            }

        });
    }
}
