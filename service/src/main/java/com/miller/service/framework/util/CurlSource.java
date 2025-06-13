package com.miller.service.framework.util;

/**
 * cURL 命令来源类型
 */
public enum CurlSource {
    /**
     * Charles 抓包工具
     */
    CHARLES,
    
    /**
     * Chrome 浏览器
     */
    CHROME,
    
    /**
     * 未知来源
     */
    UNKNOWN
} 