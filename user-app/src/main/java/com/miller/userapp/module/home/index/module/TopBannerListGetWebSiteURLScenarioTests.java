package com.miller.userapp.module.home.index.module;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.hungrypanda.app.server.common.utils.DeepLinkUtils;
import com.hungrypanda.app.server.vo.index.SingleModuleVo;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.index.module.flow.IndexOperateModuleListFlow;
import com.miller.userapp.module.home.index.module.response.IndexOperateModuleListResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/6/27 20:52
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-首页资源位-中文版-banner-跳转类型为网页-展示")
@Log4j2
public class TopBannerListGetWebSiteURLScenarioTests {

    @DisplayName("正常流程_获取跳转类型为网页的banner")
    @Test
    public void topBannerListGetWebSiteURLSuccessfully() {

        IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();

        List<SingleModuleVo> topBannerList = indexOperateModuleList.getResult().getIndexModuleVo().getTopBannerList();

        SingleModuleVo singleModuleVo = topBannerList.stream()
                .filter(banner -> banner.getIndexModuleDetailId() == Integer.parseInt(PropertiesUtils.loadProperties().getProperty("user.app.index.banner.index.module.detailId.type2")))
                .findFirst()
                .get();
        String type = singleModuleVo.getType().toString();

        Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(singleModuleVo.getUrl());

        assertThat(indexOperateModuleList.getSuccess()).isTrue();
        assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
        assert type.equals("2");

    }

}
