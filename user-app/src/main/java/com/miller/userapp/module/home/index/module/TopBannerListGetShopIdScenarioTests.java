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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/7/11 19:47
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-首页资源位-中文版-banner-跳转类型为店铺id-展示")
@Log4j2
public class TopBannerListGetShopIdScenarioTests {
    @DisplayName("正常流程_获取跳转类型为店铺的banner")
    @Test
    public void topBannerListGetShopIdSuccessfully() {

        IndexOperateModuleListResponseDTO indexOperateModuleList = new IndexOperateModuleListFlow().getIndexOperateModuleList();


        SingleModuleVo singleModuleVo = indexOperateModuleList.getResult()
                .getIndexModuleVo()
                .getTopBannerList()
                .stream()
                .filter(banner -> banner.getIndexModuleDetailId() == Integer.parseInt(new PropertiesUtils().getProperty(this.getClass(),"user.app.index.banner.index.module.detailId.type1")))
                .findFirst()
                .get();
        Map<String, String> map = DeepLinkUtils.extraDeepLinkSearchParams(singleModuleVo.getUrl().toString());
        assertThat(indexOperateModuleList.getSuccess()).isTrue();
        assert indexOperateModuleList.getResultCode() == ResultCode.SUCCESS.getCode();
        assert singleModuleVo.getType() == 1;
        assert map.containsKey("shopId");

    }

}
