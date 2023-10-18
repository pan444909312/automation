package com.miller.service.framework.apidoc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("YApiUtils TestSuite")
class YApiUtilsTest {

    @Disabled
    @Test
    @DisplayName("Should be updated website field values successfully.")
    void testYApiUtils() {
        var yApiUri = YApiUtils.Y_API_DOMAIN + "/project/60/interface/api/3040";
        var yApiAPIInfoDTO = YApiUtils.getYApiInfoByID(yApiUri);
        var id = yApiAPIInfoDTO.getData().getId();
        assertThat(id, Matchers.notNullValue());
        YApiUtils.updateYApiData(yApiUri);
    }

}