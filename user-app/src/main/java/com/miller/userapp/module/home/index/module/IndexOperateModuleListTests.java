package com.miller.userapp.module.home.index.module;

import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungrypanda.app.server.api.common.ResultCode;
import com.hungrypanda.app.server.common.utils.DeepLinkUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.home.index.module.flow.IndexOperateModuleListFlow;
import com.miller.userapp.module.home.index.module.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.module.person.address.list.flow.AddressListFlow;
import com.miller.userapp.module.person.address.list.response.AddressListResponseDTO;
import com.miller.userapp.util.UrlUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/6/27 20:52
 */
@EnvTag.Test
@TestFramework
@DisplayName("首页资源位-banner")
@Log4j2
public class IndexOperateModuleListTests {

    IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();


    @DisplayName("正常流程_获取跳转类型为活动专题的banner")
    @Test
    public void topBannerListGetModuleActivitySuccessfully() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(indexOperateModuleList);
            List<Map<String, Object>> topBannerList = JSONPath.read(jsonString, "$.result.indexModuleVo.topBannerList", List.class);

            Map<String, Object> topBanner = topBannerList.get(0);

            String type = topBanner.get("type").toString();
            Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(topBanner.get("url").toString());
            String url = URLDecoder.decode(map.get("url"), "UTF-8");

            assertThat(indexOperateModuleList.getSuccess()).isTrue();
            assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
            assert type.equals("3");
            assert UrlUtils.isValidUrl(url,"https://free-edition-f2e-test.hungrypanda.cn","activityId");

        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("正常流程_获取跳转类型为店铺的banner")
    @Test
    public void topBannerListGetShopIdSuccessfully() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(indexOperateModuleList);
            List<Map<String, Object>> topBannerList = JSONPath.read(jsonString, "$.result.indexModuleVo.topBannerList", List.class);
            Map<String, Object> topBanner = topBannerList.get(1);

            String type = topBanner.get("type").toString();
            Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(topBanner.get("url").toString());


            assertThat(indexOperateModuleList.getSuccess()).isTrue();
            assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
            assert type.equals("1");
            assert map.keySet().contains("shopId");
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
    }

    @DisplayName("正常流程_获取跳转类型为老邀新的banner")
    @Test
    public void topBannerListGetAlwaysInviteNewSuccessfully() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(indexOperateModuleList);
            List<Map<String, Object>> topBannerList = JSONPath.read(jsonString, "$.result.indexModuleVo.topBannerList", List.class);
            Map<String, Object> topBanner = topBannerList.get(2);

            String type = topBanner.get("type").toString();
            Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(topBanner.get("url").toString());
            String url = URLDecoder.decode(map.get("url"), "UTF-8");


            assertThat(indexOperateModuleList.getSuccess()).isTrue();
            assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
            assert type.equals("18");
            assert UrlUtils.isValidUrl(url,"https://f2e-pkg-leaflet-test.hungrypanda.cn/alwaysInviteNew","");
        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
