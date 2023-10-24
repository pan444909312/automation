package com.miller.service.framework.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Miller Shan
 */
class PhoneNumberUtilsTest {

    @Test
    void generatePhoneNumber() {
        var size = 1000;
        Set<String> total = new HashSet<>();
        for (var i = 0; i < size; i++) {
            String phoneNumber = PhoneNumberUtils.generatePhoneNumber();
            if (total.contains(phoneNumber)) {
                throw new RuntimeException("重复:" + phoneNumber);
            } else {
                total.add(phoneNumber);
            }
        }
        // 如果不相等则可能有重复的元素
        assertEquals(size, total.size());
    }
}