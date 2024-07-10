package com.miller.takeaway.order;

import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * 运行测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/10 11:17:56
 */
public class RunTestCase {
    public static LauncherDiscoveryRequest run() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.miller.takeaway.order.branch.settlement")
                ).filters(
                        includeClassNamePatterns(".*Scenario[s]?Test[s]?")
                ).build();
        return request;
    }
}
