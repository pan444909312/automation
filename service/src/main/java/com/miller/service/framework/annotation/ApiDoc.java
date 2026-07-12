package com.miller.service.framework.annotation;

import com.miller.service.framework.listenner.TestResultWatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 框架与接口文档交互处理器
 * <p>
 * 通过{@link ApiDoc @ApiDoc}注解完成接口框架与接口文档的同步。
 * 配合{@link TestResultWatcher TestExecutedWatcher}监听器一起使用，
 * 会在测试用例执行成功自动更新YApi中的数据(Tag和备注)。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/9 18:05:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiDoc {
    String value();
}
