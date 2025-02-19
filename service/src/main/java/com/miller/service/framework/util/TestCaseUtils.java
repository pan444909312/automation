package com.miller.service.framework.util;

/**
 * 测试用例工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/2/19 10:48:30
 */
public class TestCaseUtils {
    /**
     * 获取执行人员
     *
     * @return 执行人员
     */
    public static String getExecutor(Class<?> cls) {
//        // 执行人优先级使用注解中的作者。有问题：用例作者可能和执行人不是同一个人
//        Scenario scenario = cls.getDeclaredAnnotation(Scenario.class);
//        if (Objects.nonNull(scenario) && !scenario.author().isBlank()) {
//            String author = scenario.author();
//            return author.split("@")[0];
//        }
        String executor;
        String hostNameOfOS = OSUtils.getHostNameOfOS();
        // 如果是测试环境，则执行人员为DevOps平台
        if (hostNameOfOS.contains("hk-test-")) {
            executor = "DevOps Platform";
        } else {
            // 获取git用户名
            executor = JGitUtils.getGitEmail().split("@")[0];
        }
        return executor;
    }
}
