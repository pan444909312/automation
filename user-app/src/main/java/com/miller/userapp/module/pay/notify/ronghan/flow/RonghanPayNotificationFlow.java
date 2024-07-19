package com.miller.userapp.module.pay.notify.ronghan.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.pay.notify.ronghan.request.RonghanPayNotificationRequest;
import com.miller.userapp.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;


public class RonghanPayNotificationFlow {
    private static final String uri = PropertiesUtils.getProperty("pay.server.notification.app.url.domain")+ "/api/pandaPay/v1/notify/payment/rongHanPay/v1";
    private static final List<String> NO_SIGN_PARAMETER = Arrays.asList(
            "originTransactionId", "originMerchantTxnId","customsDeclarationAmount",
            "customsDeclarationCurrency","paymentMethod", "walletTypeName","periodValue","tokenExpireTime","sign");
    public static String rongHanPayNotification(RonghanPayNotificationRequest request){
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        String content = JSON.toJSONString(request);
        TreeMap<String, String> notifyMap = JSONObject.parseObject(content, new TypeReference<TreeMap<String, String>>(){});
        String signContent = strcatValueSign(notifyMap,NO_SIGN_PARAMETER);
        String rongHanPayKey = "3b7b21432c3c441ea262e2563918789d";
        String sign = signSha256(rongHanPayKey,signContent);
        request.setSign(sign);
        return HttpUtils.sendPostRequestReturnBody(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(request), null);
    }
    private static String strcatValueSign(TreeMap<String,String> treeMap, List<String> notSignKeyList) {
        StringBuffer buffer = new StringBuffer();
        treeMap.forEach((k, v) -> {
            if (StringUtils.isNotBlank(v) && !notSignKeyList.contains(k)) {
                buffer.append(v);
            }
        });
        return buffer.toString();
    }
    private static String signSha256(String key, String toBeSignedData){

        String str=toBeSignedData + key;
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将字节转换为十六进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
