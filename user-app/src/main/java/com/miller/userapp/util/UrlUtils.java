package com.miller.userapp.util;

import com.hungrypanda.app.server.common.utils.DeepLinkUtils;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.Map;

/**
 * @author panjuxiang
 * @since 2024/6/28 11:57
 */
public class UrlUtils {

    /**
     * 传入URL，判断URL是否以传入的参数baseUrl为开头，且url是否包含传入的requiredParam参数
     * @param url
     * @param baseUrl
     * @param requiredParam
     * @return
     */
    public static boolean isValidUrl(String url, String baseUrl, String requiredParam) {
        // 检查URL是否以给定的baseUrl开头
        if (!url.startsWith(baseUrl)) {
            return false;
        }
        // 如果参数为空，则不需要检验参数 直接返回true即可
        if (StringUtils.isEmpty(requiredParam)){
            return true;
        }
        // 检查URL中是否包含所需的查询参数
        // 这里假设查询参数是通过 '&' 或 '?' 分隔的键值对
        // 注意：这个方法不检查查询参数的值是否正确，只是检查参数名是否存在
        String[] queryParams = url.split("[?&]"); // 使用 ? 或 & 分割查询参数
        for (String param : queryParams) {
            if (param.startsWith(requiredParam + "=")) { // 检查参数名是否匹配
                return true;
            }
        }
        return false;
    }

}
