package com.miller.userapp.module.home.index.module;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.hungrypanda.app.server.vo.index.SingleModuleVo;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.index.module.flow.IndexOperateModuleListFlow;
import com.miller.userapp.module.home.index.module.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.util.LoginUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/7/19 17:57
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-首页资源位-中文版-banner-限制新用户可见-展示")
public class TopBannerListOnlyNewUserScenarioTests {

    @BeforeAll
    void beforeAll() {
        LoginUtils.getHeaderWithAuth(0);
    }

    @DisplayName("正常流程_获取限制新用户可见的banner")
    @Test
    public void topBannerListOnlyNewUserSuccessfully() {

        IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();

        List<SingleModuleVo> topBannerList = indexOperateModuleList.getResult().getIndexModuleVo().getTopBannerList();

        SingleModuleVo singleModuleVo = topBannerList.stream()
                .filter(banner -> banner.getIndexModuleDetailId() == Integer.parseInt(new PropertiesUtils().getProperty(this.getClass(),"user.app.index.banner.index.module.detailId.newUser")))
                .findFirst()
                .get();

        assertThat(indexOperateModuleList.getSuccess()).isTrue();
        assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
        assert singleModuleVo.getIndexModuleDetailId() == Integer.parseInt(new PropertiesUtils().getProperty(this.getClass(),"user.app.index.banner.index.module.detailId.newUser"));

    }
}
