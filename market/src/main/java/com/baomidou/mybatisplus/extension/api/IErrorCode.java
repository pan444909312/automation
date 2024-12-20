
package com.baomidou.mybatisplus.extension.api;

/**
 * 请勿删除此类。market 项目需要使用老版本的MBP。
 *
 * @author Miller Shan
 * @since 2024/12/19 21:41:55
 */
public interface IErrorCode {

    /**
     * 错误编码 -1、失败 0、成功
     */
    long getCode();

    /**
     * 错误描述
     */
    String getMsg();
}
