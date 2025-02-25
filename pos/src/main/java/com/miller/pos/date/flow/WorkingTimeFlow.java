package com.miller.pos.date.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.date.request.WorkingTimeDTO;
import com.miller.pos.util.RequestUtils;
import com.squareup.moshi.Json;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static cn.hutool.poi.excel.sax.AttributeName.s;

@Slf4j
public class WorkingTimeFlow {

    private static final String uri = "https://date.appworlds.cn/work/days";
    private static final String DATA_FORM = "yyyy-MM-dd";

    /**
     * 获取时间范围内的工作日数量
     * @param startDate 开始时间  格式 ： 2025-02-05
     * @param endDate 截止时间 格式: 2025-02-05
     * @return 工作日数
     */
    public static int getDays(String startDate,String endDate){

        int days = 0;

        // 校验时间格式
        final DateFormat sdf = new SimpleDateFormat(DATA_FORM);
        sdf.setLenient(false);
        try {
            sdf.parse(startDate);
            sdf.parse(endDate);
        } catch (ParseException e) {
            log.error("时间格式错误，开始：{},结束:{},默认赋值：0",startDate,endDate);
            return days;
        }


        Map<String,Object> params = new HashMap<>();
        params.put("startDate",startDate);
        params.put("endDate",endDate);
        WorkingTimeDTO workingTime = RequestUtils.sendGetRequestReturnJavaObject(uri, params, new HashMap<>(), new HashMap<>(), WorkingTimeDTO.class);
        if (ObjectUtils.isNotEmpty(workingTime)){
            days = workingTime.getData();
        }
        return days;
    }


}
