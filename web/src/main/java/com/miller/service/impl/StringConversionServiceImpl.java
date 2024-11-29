package com.miller.service.impl;

import com.miller.service.StringConversionService;
import com.miller.entity.dao.StringConversionDto;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
public class StringConversionServiceImpl implements StringConversionService {

    @Override
    public String toPublishingFormat(StringConversionDto stringConversionDto) {
        Map<String, List<String>> resMap = null;
        if (stringConversionDto.getType().equals("toServerGroup")) {
            resMap = this.toServerGroup(stringConversionDto.getSourceValue());
        } else {
            resMap = this.toDeveloperGroup(stringConversionDto.getSourceValue());
        }

        //  返回结果数据拼接
        StringBuffer resStr = new StringBuffer();
        resMap.forEach((key, values) -> {
            resStr.append("【").append(key).append("】").append("\n");
            values.forEach(v -> resStr.append("\t").append(v).append("\n"));
        });
        return resStr.toString();
    }
    @Override
    public Map<String, List<String>> toServerGroup(String sourceValue) {
        // 数据拆分组合
        String[] rows = sourceValue.split("\n");
        Map<String, List<String>> resMap = new HashMap<>();
        for (String row : rows) {
            List<String> colList = Arrays.asList(row.split("\t"));
            String key = colList.get(0).trim();

            colList = colList.subList(1, colList.size());
            String value = String.valueOf(colList).trim();
            value = value.substring(1, value.length() - 1);

            List<String> valueList = resMap.containsKey(key) ? resMap.get(key) : new ArrayList<>();
            valueList.add(value);
            resMap.put(key, valueList);
        }

        return resMap;
    }

    public Map<String, List<String>> toDeveloperGroup(String sourceValue) {
        // 数据拆分组合
        String[] rows = sourceValue.split("\n");
        Map<String, List<String>> resMap = new HashMap<>();
        for (int i = 0; i < rows.length; i++) {
            List<String> colList = Arrays.asList(rows[i].split("\t"));
            String key = colList.get(colList.size() - 1).trim();

            String value = String.valueOf(colList).trim();
            value = value.substring(1, value.length() - 1);

            List<String> valueList = resMap.containsKey(key) ? resMap.get(key) : new ArrayList<>();
            valueList.add(value);
            resMap.put(key, valueList);
        }
        return resMap;
    }
    public static void main(String[] args) {
        //  发布服务元数据
        String sourceValue = "hp-user-app-server\tfeature/ID1064724\t业务需求\t结算页优惠强化\t金思远\t李攀\t陈心桐\n" +
                "message-server\tfeature/ID1066461_username_change\t业务需求\t【C侧】用户端手机号换绑\t姚裕辉\t鲁伟、严灿灿\t贾三丰、朱元杰\n" +
                "pay-service\tfeature/ID1066461_username_change\t业务需求\t【C侧】用户端手机号换绑\t姚裕辉\t鲁伟\t贾三丰、朱元杰\n" +
                "hp-user-app-server\tfeature/ID1066461_username_change\t业务需求\t【C侧】用户端手机号换绑\t姚裕辉\t鲁伟、严灿灿\t贾三丰、朱元杰\n" +
                "hp-user-app-server\tfeature/ID1066515\t业务需求\t【C侧】会员合单V4.0：店铺页面支持加购会员合单\t曾嘉晨\t鲁伟、杨勤勤\t陈心桐\n" +
                "hp-common\tfeature/ID1067795\t业务需求\t【首页资源】展示大促入口时，隐藏banner、顶通\t葛东杰\t严灿灿\t温心成\n" +
                "hp-erp-server\tfeature/ID1067795\t业务需求\t【首页资源】展示大促入口时，隐藏banner、顶通\t葛东杰\t严灿灿\t温心成\n" +
                "hp-user-app-server\tfeature/ID1067795\t业务需求\t【首页资源】展示大促入口时，隐藏banner、顶通\t葛东杰\t严灿灿\t温心成\n" +
                "hp-erp-server\tfeature/ID1066496_venue\t业务需求\t【C侧】会场v3.0-基础能力完善\t葛东杰\t胡杨\t肖进川\n" +
                "hp-user-app-server\tfeature/ID1066496_venue\t业务需求\t【C侧】会场v3.0-基础能力完善\t葛东杰\t胡杨\t肖进川\n" +
                "hp-erp-server-job\tfeature/ID1066496_venue\t业务需求\t【C侧】会场v3.0-基础能力完善\t葛东杰\t胡杨\t肖进川\n" +
                "hp-erp-server\tfeaure/ID1066620_lottery_optimization\t业务需求\t【C侧】抽奖活动曝光位及配置优化\t李菁菁\t单东东\t源宇凯\n" +
                "hp-user-app-server\tfeaure/ID1066620_lottery_optimization\t业务需求\t【C侧】抽奖活动曝光位及配置优化\t李菁菁\t张培\t源宇凯\n" +
                "message-server\tfeature/ID1066620\t业务需求\t【C侧】抽奖活动曝光位及配置优化\t李菁菁\t张培\t高波\n" +
                "market-admin\tfeature/ID1066045\t技术优化\t【【B侧】PF SQL优化】\t陈晓涵\t吕志鹏\t吕志鹏\n" +
                "hp-promotion-server\tfeature/ID1067706_super_base_price\t业务需求\t【C侧】智能营销-2024Q4_3张神券基础值算法浮动\t赵春亮\t严灿灿\t刘阳\n" +
                "recommend-sort-server\tfeature/ID1067706_super_base_price\t业务需求\t【C侧】智能营销-2024Q4_3张神券基础值算法浮动\t赵春亮\t严灿灿\t刘阳\n" +
                "hp-user-app-server\tfeature/ID1067706_super_base_price\t业务需求\t【C侧】智能营销-2024Q4_3张神券基础值算法浮动\t赵春亮\t严灿灿\t刘阳";

        StringConversionService scs = new StringConversionServiceImpl();
//        Map<String, List<String>> resMap = scs.toDeveloperGroup(sourceValue); // 根据开发人员分组输出
        Map<String, List<String>> resMap = scs.toServerGroup(sourceValue); // 根据发布服务分组输出



        // 打印输出
        List<String> keys = resMap.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        keys.forEach(key ->{
            System.out.println("【".concat(key).concat("】"));
            List<String> values = resMap.get(key);
            for (int i = 0; i < values.size(); i++) {
                System.out.println("\t".concat(String.valueOf(i + 1)).concat(". ").concat(values.get(i)));
            }
        });

    }


}
