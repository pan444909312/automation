package com.miller.service.framework.action;

import com.miller.service.framework.asserts.AssertUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 框架支持的关键字、函数、自定义方法
 *
 * @author Miller Shan
 * @version 1.0
 */
@Slf4j
public class Actions {
    public static Object executeKeyword(ActionsEnum action, Object[] args) {
        return executeKeyword(action.name(), args);
    }

    /**
     * 执行关键字
     *
     * @param action 关键字名称
     * @param args   关键字参数
     * @return 执行结果
     */
    public static Object executeKeyword(String action, Object[] args) {
        // 关键字执行结果
        Object keywordExecuteResult = null;
        try {
            log.debug("开始执行关键字:{}", action);
            boolean isEnum = ActionsEnum.isEnum(action);
            if (!isEnum) {
                log.error("使用了系统不支持的关键字：{}", action);
                return null;
            }
            ActionsEnum enumActions = ActionsEnum.valueOf(action); // 将字符串转成枚举类型

            switch (enumActions) {
                case invariantValue:
                    return String.valueOf(args[0]); // 固定值无需处理直接返回即可
                case none:
                    // none 无需处理直接返回即可
                    return null;
                case equals:
                    return AssertUtils.assertThat(args[0], args[1]);
                default:
                    log.error("使用了系统不支持的关键字:{}", action);
                    throw new IllegalArgumentException("使用了系统不支持的关键字:{}" + action);
                    // return null;
            }
        } finally {
            log.debug("结束执行关键字:{} , 执行结果:{}", action, keywordExecuteResult);
        }
    }
}
