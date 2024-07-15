package com.miller.service.framework.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OSUtilsTest {

    @Test
    void getUserNameOfOS() {
        assertNotNull(OSUtils.getUserNameOfOS());
    }

    @Test
    void getOSName() {
        String osName =OSUtils.getOSName();
        System.out.println("Detected OS: " + osName);
        if (osName.startsWith("Windows")) {
            System.out.println("The system is Windows.");
        } else if (osName.startsWith("Mac")) {
            System.out.println("The system is Mac OS.");
        } else if (osName.startsWith("Linux")) {
            System.out.println("The system is Linux.");
        } else if (osName.startsWith("Unix") || osName.startsWith("SunOS")) {
            System.out.println("The system is Unix-like.");
        } else {
            System.out.println("Unknown operating system.");
        }
        assertNotNull(OSUtils.getOSName());
    }
}