package com.miller.bdm.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fly on 2020/9/15.
 */
public class SignGenerateUtil {

    private static Logger logger = LoggerFactory.getLogger(SignGenerateUtil.class);


    private static Pattern pattern = Pattern.compile("\\{.*\\}|\\[.*\\]");

    /**
     * 签名生成算法
     *
     * @return sign
     * @param请求的所有参数必须已转换为字符串类型
     */
    public static String getSign(JSONObject json, String appSecret) {
        Map<String, String> params = toHashMap(json);

        // 第一步：检查参数是否已经排序
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for (Object key : keys) {
            query.append(key);
            if (!key.equals("file")) {//如果文件不计算在签名内
                String value = params.get(key.toString());
                //参数值如果是json数组、json类型、空值，都将不计算在签名内
                if (valueTypeQualified(value)) {
                    query.append(value);
                }
            }
        }

        String str = appSecret + query.toString() + appSecret;
        logger.error("待加密字符串：" + str);
        try {
            str = new String(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("getSign异常={}", e);
        }
        return encrypt(str, "MD5");

    }

    /**
     * 如果某个参数值类型为数组，将不计算在签名算法内
     *
     * @param str
     * @return
     */
    private static boolean valueTypeQualified(String str) {
        if (StringUtils.isBlank(str) || str.equals("null")) {
            return false;
        }
        Matcher matcher = pattern.matcher(str);
        return !matcher.matches();
    }

    /**
     * 将json格式的字符串解析成Map对象
     */
    private static HashMap<String, String> toHashMap(JSONObject jsonObject) {
        HashMap<String, String> map = new HashMap<String, String>(jsonObject.entrySet().size());
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            map.put(key, value);
        }
        return map;
    }


    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.isEmpty()) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            logger.error("encrypt异常={}", e);

            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuilder des = new StringBuilder();
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }

    public static void main(String[] args) throws JSONException {
        JSONObject json = JSON.parseObject("{'appTypeId':0,'sign':'e30c2b49fc5379bba21da5a980484306','pkg':0,'pageNum':1,'uri':'MyOrders','terminalType':'2','pvid':'J056B3D6-44E8-4DAC-B1BC-4B1F5F942E08','countryCode':'HK','userId':43312029,'tokenNew':'addafae9d32f414b91aabf929c3ede15','cookieId':'FD20A936-9212-4C85-9460-B23D012E7467','orderType':'4','sid':'E8C3817D33E9442BA0884A2EF4874371','userToken':'Pu9ZPsOmyP\\/0pegyf7aUcgQQ','lang':0,'appVersion':'5.4','currency':'HKD','appTimestamp':1538041791623,'appKey':'lrk34j59fjv'}");
        json.remove("sign");
        System.out.println(getSign(json, "j2193ugkl39vlk23jh87f213988sd"));
        //System.out.println(System.currentTimeMillis() +"\r\n"+ SignGenerate.getMD5("123"));

//        String app_secret="24242";
//        Map<String,String> map=new HashMap<String,String>();
//        map.put("userName","test");
//        map.put("userId","1134");
//        map.put("time",System.currentTimeMillis()+"");
//
//        String str="{\n" +
//                "    \"userName\":\"2646488519@qq.com\",\n" +
//                "    \"password\":\"fg4s59gj3tl5lg9dg834kkdjldf\",\n" +
//                "  \"terminalType\":1,\n" +
//                "  \"loginType\":1,\n" +
//                "  \"handPassword\":\"\",\n" +
//                "  \"lang\":1\n" +
//                " \n" +
//                "}\n";
//        JSONObject jsonObj = JSON.parseObject(str);
//        System.out.println(jsonObj);
//        String sign=getSign(jsonObj,app_secret);
//        System.out.println(sign);
    }


}
