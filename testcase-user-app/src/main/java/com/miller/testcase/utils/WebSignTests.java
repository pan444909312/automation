package com.miller.testcase.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miller.service.framework.http.HttpUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 现在我有一个需求：我正在做接口测试，前端使用了sign.js进行前端的参数签名。我使用 WebSignTests.java想 要实现和前端 sign.js 相同的功能，但是发现可能是我代码写错了，导致签名失败。我编写了一个main()方法调用 HttpUtils.sendPostRequestReturnBody(uri, params, headers, body, null)用于验证验签码是否正确.
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/6/16 10:56:29
 */
public class WebSignTests {

    public static void main(String[] args) throws Exception {
        String uri = "https://api-cn-f2e-test.hungrypanda.cn/api/app/user/v1/address/edit";
        String method = "POST";

        // 使用LinkedHashMap保持字段顺序
        Map<String, Object> headers = new LinkedHashMap<>();
        headers.put("sec-fetch-mode", "cors");
        headers.put("referer", "https://f2e-web-test.hungrypanda.cn/");
        headers.put("sec-fetch-site", "same-site");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,ko;q=0.7,zh-TW;q=0.6");
        headers.put("origin", "https://f2e-web-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("Authorization", "7fde54323c03abd43836e70824f95e18");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"macOS\"");
        headers.put("accept-encoding", "gzip, deflate, br, zstd");
        headers.put("sec-fetch-dest", "empty");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
        headers.put("Content-Type", "application/json");

        // 使用LinkedHashMap保持字段顺序
        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("pm", "POST");
        
        // 只保留必要的ph字段
        Map<String, Object> ph = new LinkedHashMap<>();
        ph.put("platform", "PC_WEB_USER");
        ph.put("brand", "hungrypanda");
        ph.put("longitude", "120.21660377206526");
        ph.put("latitude", "30.20401351371808");
        ph.put("version", "8.8.0");
        ph.put("apptypeid", "1");
        ph.put("countryCode", "US");
        ph.put("language", "en");
        ph.put("authorization", "7fde54323c03abd43836e70824f95e18");
        ph.put("uniqueToken", "2b91f546-f9fe-4eca-b674-7d2e5e8cfa50");
        bodyMap.put("ph", ph);

        Map<String, Object> pd = new LinkedHashMap<>();
        pd.put("addressId", 1398680200);
        pd.put("address", "China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心");
        pd.put("addressRemark", "备注了啥啊123");
        pd.put("gender", 0);
        pd.put("longitude", "120.162482");
        pd.put("latitude", "30.20074");
        pd.put("addTag", 1);
        pd.put("countryCode", "86");
        pd.put("telephone", "15606690056");
        pd.put("contacts", "东东6");
        pd.put("houseNum", "101");
        pd.put("postcode", "330292");
        pd.put("buildingName", "星耀中心");
        pd.put("verify", 1);
        pd.put("shopId", 0);
        pd.put("type", 2);
        pd.put("isDefault", 0);
        bodyMap.put("pd",pd);

        bodyMap.put("nv", "2");

        // 生成签名
        ObjectMapper mapper = new ObjectMapper();
        String nt = String.valueOf(System.currentTimeMillis());
        String nh = mapper.writeValueAsString(headers);
        String nb = mapper.writeValueAsString(bodyMap);

        // 使用SignUtils生成签名
        WebSignUtils.SignResult signResult = WebSignUtils.encode(nt, uri, method, nh, nb);

        // 更新请求体中的签名信息
        bodyMap.put("nt", signResult.getNt());
        bodyMap.put("nn", signResult.getNn());
        bodyMap.put("nd", signResult.getNd());

        // 发送请求
        String responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, mapper.writeValueAsString(bodyMap), null);
        System.out.println("Response: " + responseBody);
    }
}
