package com.miller.service.framework.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JGitUtilsTest {

    @Test
    void getGitUsername() {
        assertNotNull(JGitUtils.getGitName());
    }

    @Test
    void getGitEmail() {
        assertNotNull(JGitUtils.getGitEmail());
    }
}