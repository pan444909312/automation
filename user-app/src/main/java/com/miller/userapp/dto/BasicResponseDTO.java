package com.miller.userapp.dto;

import com.hungrypanda.app.server.common.result.Result;
import lombok.Data;

/**
 * 基础_响应实体类。
 * 与开发的 Result
 *
 * @author Miller Shan
 * @version 1.0
// * @see com.hungrypanda.app.server.api.common.Result
 * @since 2023/12/6 16:15:26
 */
@Data
public class BasicResponseDTO<T> extends Result<T> {
    /**
     * MybatisPlus InnerInterceptor 拦截器获取执行sql值.这是在切面中获取set进去的。
     * com.hungrypanda.starter.datasource.interceptor.MybatisSqlInterceptor implements InnerInterceptor
     * 只有在 dev, test, beta 环境才会注入这个字段的值
     */
    // private Object queryList;
}
