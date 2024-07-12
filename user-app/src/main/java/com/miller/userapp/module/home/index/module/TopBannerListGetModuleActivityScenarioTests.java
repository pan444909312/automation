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
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/7/4 16:16
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-首页资源位-中文版-banner-跳转类型为活动专题-展示")
@Log4j2
public class TopBannerListGetModuleActivityScenarioTests {

    @Test
    @DisplayName("正常流程_获取跳转类型为活动专题的banner")
    public void topBannerListGetModuleActivity() {
        IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();


        try {
            SingleModuleVo singleModuleVo = indexOperateModuleList.getResult()
                    .getIndexModuleVo()
                    .getTopBannerList()
                    .stream()
                    .filter(banner -> banner.getIndexModuleDetailId() == Integer.parseInt(PropertiesUtils.loadProperties().getProperty("user.app.index.banner.index.module.detailId.type3")))
                    .findFirst()
                    .get();

            Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(singleModuleVo.getUrl());
            String url = URLDecoder.decode(map.get("url"), "UTF-8");

            assertThat(indexOperateModuleList.getSuccess()).isTrue();
            assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
            assert singleModuleVo.getType() == 3;
            assert UrlUtils.isValidUrl(url, "https://free-edition-f2e-test.hungrypanda.cn", "activityId");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
