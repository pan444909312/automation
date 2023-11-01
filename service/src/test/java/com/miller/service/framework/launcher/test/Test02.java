package com.miller.service.framework.launcher.test;

import com.miller.service.framework.annotation.EnvTag;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 17:22:58
 */
public class Test02 {
    @EnvTag.Test
    @Test
    void test01() {
        System.out.println("@EnvTag.Test");
    }

    @EnvTag.Prod
    @Test
    void test02() {
        System.out.println("@EnvTag.Prod");
    }

    @EnvTag.Dev
    @Test
    void test03() {
        System.out.println("@EnvTag.Dev");
    }
}
