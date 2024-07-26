package com.miller.service.framework.clz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ClassFindService.class)
class ClassFindServiceTest {

    @Autowired
    private ClassFindService classFindService;
    @Test
    void getPackageClass() {
        classFindService.getPackageClass("com.miller.service.framework.clz");
    }
}