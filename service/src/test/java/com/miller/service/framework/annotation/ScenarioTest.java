package com.miller.service.framework.annotation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 测试{@link Scenario @Scenario} 注解包含 {@link TestFramework @TestFramework}
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/6 16:57:41
 */
@Disabled
@TestFramework
@Scenario(scenarioID = "01J4KETZ7AFZXVWE02JSM7P3Z7", scenarioName = "测试场景", developmentTime = 1, maintenanceTime = 1, manualTestTime = 1)
public class ScenarioTest {
    @Test
    void test() {
        System.out.println(this.getClass().getName());
    }
}
