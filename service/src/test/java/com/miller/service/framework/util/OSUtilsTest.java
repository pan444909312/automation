package com.miller.service.framework.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OSUtilsTest {

    @Test
    void getUserNameOfOS() {
        assertNotNull(OSUtils.getUserNameOfOS());
    }
}