package com.miller.service.impl;

import com.miller.entity.dto.StringConversionDto;
import com.miller.service.StringConversionService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Map<String, List<String>> toServerGroup(String sourceValue) {
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


}
