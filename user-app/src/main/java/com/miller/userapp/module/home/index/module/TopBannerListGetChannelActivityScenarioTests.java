package com.miller.userapp.module.home.index.module;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.hungrypanda.app.server.common.utils.DeepLinkUtils;
import com.hungrypanda.app.server.vo.index.SingleModuleVo;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.index.module.flow.IndexOperateModuleListFlow;
import com.miller.userapp.module.home.index.module.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.util.UrlUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/7/18 17:40
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-首页资源位-中文版-banner-跳转类型为品类专题页-展示")
public class TopBannerListGetChannelActivityScenarioTests {

    @DisplayName("正常流程_获取跳转类型为品类专题页的banner")
    @Test
    public void topBannerListGetChannelActivitySuccessfully() {

        IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();

        List<SingleModuleVo> topBannerList = indexOperateModuleList.getResult().getIndexModuleVo().getTopBannerList();

        SingleModuleVo singleModuleVo = topBannerList.stream()
                .filter(banner -> banner.getIndexModuleDetailId() == Integer.parseInt(new PropertiesUtils().getProperty(this.getClass(),"user.app.index.banner.index.module.detailId.type20")))
                .findFirst()
                .get();

        Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(singleModuleVo.getUrl());
        Set<String> keySet = map.keySet();

        assertThat(indexOperateModuleList.getSuccess()).isTrue();
        assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
        assert singleModuleVo.getType() == 20;
        assert keySet.contains("channelId");


    }
}
