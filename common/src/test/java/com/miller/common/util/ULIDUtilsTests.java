package com.miller.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class ULIDUtilsTests {

    @RepeatedTest(20)
    public void testGenerateULID() {
        String ulid = ULIDUtils.generateULID();
        Assertions.assertNotNull(ulid);
        //System.out.println(ulid);
    }
}
