package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.memberShopPacket.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;

import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class ShopListABFow {
  /**
     * 接口_首页店铺流
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";

    /**
     * 流程_获取首页店铺流
     */
    public static ShopListResponseDTO getShopList(ShopListRequestDTO shopListRequestDTO) {
//        设置header 中testGroup 为对照组
        RequestUtils.getHeaders().put("testGroup","I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,S_H_R_L_TEST_GROUP_1,23,29,30,31,32,NUMBER_MASKING_00,33,34,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST02,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ02,TJTCX01,YBXS02,CCPRO01,ZDFQ02,SKXRB01,ABT02,XRTC01,QYTCD01,SMSS02,XMLM02,RRREC01,ZFBMM01,SSJLY01,SPSS01,MRBX02,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA01,WLTC01,SPM01");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopListRequestDTO), null, ShopListResponseDTO.class);
    }
}
