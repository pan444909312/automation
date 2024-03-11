package com.miller.merchant.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hungrypanda.app.server.api.common.Result;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础_响应实体类
 *
 * @author Miller Shan
 * @version 1.0
 * @see com.panda.common.base.AppResult
 * @since 2023/12/6 18:15:26
 */
@Data
public class BasicResponseDTO<T> extends Result<T> implements Serializable {
    /*
    开发代码使用的是 AppResult , 但是使用这个对象会导致偶发性JSON序列化报错
    "com.alibaba.fastjson.JSONException: No enum ordinal com.panda.common.constants.PResultCode.1000"
     而且发现这个对象里面多处两个字段blankList，blankObj 但是这两个字段开发代码中又没有用上，不知道写的有啥用。

     */
    /**
     * 空列表集
     */
    public static JSONArray blankList = new JSONArray();
    /**
     * 空对象
     */
    public static JSONObject blankObj = new JSONObject();
}
