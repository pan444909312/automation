package com.miller.data.center.user;

import com.hungrypanda.app.server.api.common.Result;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础_响应实体类。
 * 无法使用开发的 Result 类，因为含有枚举，反序列化的时候会失败
 *
 * @author Miller Shan
 * @version 1.0
 * // * @see com.hungrypanda.app.server.api.common.Result
 * @since 2023/12/21 16:15:26
 */
@Data
public class BasicResponseDTO<T> extends Result<T> implements Serializable {
    /**
     * MybatisPlus InnerInterceptor 拦截器获取执行sql值.这是在切面中获取set进去的。
     * com.hungrypanda.starter.datasource.interceptor.MybatisSqlInterceptor implements InnerInterceptor
     * 只有在 dev, test, beta 环境才会注入这个字段的值
     */
    private Object queryList;
}
