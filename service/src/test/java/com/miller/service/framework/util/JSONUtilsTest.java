package com.miller.service.framework.util;

import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Miller Shan
 */
class JSONUtilsTest {

    @Test
    @DisplayName("Asset String is JSON format.")
    void isJSONFormat() {
        String errorJson = "{\"A\":\"B\"}x";
        Assertions.assertThrows(JSONException.class, () -> JSONUtils.isJSONFormat(errorJson));
        String json = "{\"A\":\"B\"}";
        assertTrue(JSONUtils.isJSONFormat(json));
    }

    @Test
    @DisplayName("Testing JSON Exception")
    void testJSONException() {
        var json = "{\"resultCode\":1000,\"error\":\"成功\",\"reason\":\"成功\",\"result\":{\"filterGroup\":[{\"id\":5,\"groupType\":2,\"groupName\":\"巡航栏\",\"multiSelect\":false,\"filters\":[{\"id\":22,\"filterName\":\"距离优先\",\"iconUrl\":null,\"iconActiveUrl\":null,\"imgUrl\":null,\"imgActiveUrl\":null,\"count\":null,\"filterType\":1,\"sort\":null,\"sortNew\":null},{\"id\":2,\"filterName\":\"自取折扣\",\"iconUrl\":null,\"iconActiveUrl\":null,\"imgUrl\":null,\"imgActiveUrl\":null,\"count\":null,\"filterType\":0,\"sort\":null,\"sortNew\":null}]}],\"shopList\":[{\"shopId\":1733,\"address\":\"中国浙江省杭州市滨江区正泰量测技术股份有限公司\",\"shopName\":\"花店222221111111111111111111111111111111\",\"shopNameEn\":null,\"bgImage\":null,\"shopBgImgEn\":null,\"shopImg\":\"http://static.hungrypanda.global/panda/159825723782121fe0b07eaa64a2b8772e27038c8c928.jpg\",\"shopStatus\":0,\"onlinePay\":2,\"praiseAverage\":\"4.6\",\"overtime\":null,\"punctuality\":null,\"pandaSend\":0,\"firstOrderDiscounts\":0,\"firstOrderDiscountsStr\":null,\"startSendMoney\":1300,\"startSendMoneyStr\":null,\"sendMoney\":0,\"sendMoneyStr\":\"$0\",\"distance\":\"0.63km\",\"time\":null,\"country\":\"美国\",\"shopLabelUrl\":\"\",\"newShopLabelUrl\":\"\",\"shopBorderUrl\":null,\"takeRateStr\":\"0.9\",\"shopFirstDiscount\":0,\"preorderClosedSupport\":0,\"deliveryFarawayType\":null,\"productDiscountTagDesc\":\"\",\"memberShopPrice\":0,\"recommendSort\":421,\"shopLogo\":\"https://static.hungrypanda.global/panda/15984965674892f1ba8f9ff7046e2a1faf2d2ac5af051.jpg?x-oss-process=style/shop_logo\",\"pcShopImg\":null,\"pcShopImgBg\":null,\"shopLabel\":0,\"isUserPack\":1,\"takeOutable\":1,\"makeTime\":5,\"deliveryType\":1,\"shopType\":0,\"perCapitaConsume\":0,\"isDel\":null,\"deliveryRange\":46080,\"showShopEvaluation\":1,\"longitude\":\"120.22252\",\"latitude\":\"30.205983\",\"fullSubList\":[{\"fullSubId\":null,\"fullSubName\":\"满50减49\",\"shopId\":null,\"full\":5000,\"fullStr\":\"50\",\"sub\":4900,\"subStr\":\"49\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"},{\"fullSubId\":null,\"fullSubName\":\"满5减3\",\"shopId\":null,\"full\":500,\"fullStr\":\"5\",\"sub\":300,\"subStr\":\"3\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"},{\"fullSubId\":null,\"fullSubName\":\"满20减2\",\"shopId\":null,\"full\":2000,\"fullStr\":\"20\",\"sub\":200,\"subStr\":\"2\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"}],\"evaluation\":\"\",\"distanceD\":631.3979527780708,\"deliveryPostcodeInfo\":null,\"newShopLabelStr\":\"\",\"discountSkuNum\":0,\"indexListLabel\":\"\",\"redPacketRate\":\"3\",\"redPacketPriceTotal\":6800,\"firstVoucherGoodsPrice\":0,\"firstVoucherSalePrice\":0,\"minNewUserSkuPrice\":-1,\"isSupermarket\":0,\"shopBgimageCN\":null,\"recommendSortEasi\":421,\"exclusive\":0,\"newShopTag\":\"\",\"praiseAverageNew\":\"4.6\",\"sendMoneyMsg\":\"配送$0起\",\"hideBusinessName\":1,\"shopPacketPricePriorityRedPacketAmount\":3000,\"shopPacketRatePriorityRedPacketRate\":3.0,\"shopPacketPricePriorityRedPacketAmountEn\":0,\"shopPacketRatePriorityRedPacketRateEn\":3.0,\"shopPacketPricePriorityRedPacketAmountNoThr\":0,\"shopPacketRatePriorityRedPacketRateNoThr\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThrEn\":0,\"shopPacketRatePriorityRedPacketRateNoThrEn\":0.0,\"shopPacketPriceSubsidy\":null,\"shopPacketPriceNoThrSubsidy\":null,\"shopPacketRateSubsidy\":null,\"shopPacketRateNoThrSubsidy\":null,\"memberPacketPriceSubsidy\":null,\"memberPacketPriceToRateSubsidy\":null,\"newChannelLabelUrl\":\"\",\"supportedLanguageCodes\":[\"EN\",\"CN\"],\"hotSalesProductIdList\":null,\"tagType\":0,\"fullSubInfo\":null,\"distanceFarawayStart\":null,\"distanceFarawayEnd\":null,\"maxDeliveryRangeMeter\":null,\"easiMaxDeliveryRangeMeter\":null,\"overAffordOrderLimit\":null,\"searchMatch\":null,\"downGrade\":null,\"cancelOrderDegrade\":null,\"memberDeliveryDiscount\":0,\"productDiscountPromoteType\":null,\"shopRank\":\"\",\"rankNo\":null,\"tobaccoShop\":0,\"specialProductIdList\":null,\"recProductType\":null,\"preorderOpenType\":1,\"fixedTagSts\":0,\"fixedTagFilter\":0,\"repeatCustomer\":null,\"specialPromote\":null,\"dineInSame\":null,\"mealsOnTime\":null,\"marketingProductIdList\":[73191,509365,509366,509367,509373,509498],\"activityTagType\":null,\"pandaLeagueTag\":0,\"pandaLeagueFilter\":0,\"shopPacketAmountPandaLeague\":0,\"shopPacketDiscountPandaLeague\":0,\"shopPacketAmountPandaLeagueEn\":0,\"shopPacketDiscountPandaLeagueEn\":0,\"subsidyPrice\":null,\"subsidyRateMaxPrice\":null,\"subsidyRate\":null,\"offlineMakeUpRate\":null,\"discountTakeSelf\":null,\"weightValue\":421.63,\"predictDeliveryTime\":null,\"isShowDelivery\":1,\"moduleSort\":null,\"listPostCodes\":null,\"prices\":null,\"shopIsTakeType\":1,\"outOfRange\":null,\"shopMarketTagDesc\":null,\"marketShowType\":null,\"moduleActivityShopId\":null,\"shopNameSort\":null,\"city\":\"测试\",\"products\":null,\"productVOList\":null,\"memberShopPriceTotal\":0,\"score\":\"NaN\",\"lastDeliveryPrice\":null,\"showShopEvaluationNum\":null,\"shopBgimageEn\":null,\"shopBgimageCn\":null,\"createTime\":0,\"updateTime\":0,\"isCollect\":0,\"productList\":null,\"promotionLabel\":null,\"promotionFullSub\":null,\"currency\":null,\"merchantCategoryId\":1254,\"merchantCategoryName\":\"水果生鲜\",\"largeRedPacketList\":null,\"redPacketList\":null,\"shippingDiscount\":null,\"averageFullSub\":null,\"discountOn\":null,\"discountRate\":null,\"saId\":null,\"showAd\":null,\"deliveryFence\":null,\"predicts\":\"{\\\"predictHandler\\\":\\\"default\\\",\\\"score\\\":421.63,\\\"time\\\":1709877221434,\\\"version\\\":1}\",\"deliveryStatus\":1,\"shopPromoteList\":[{\"showContent\":\"满减最高减$49\",\"subsidyContent\":null,\"showContentList\":null,\"type\":2,\"style\":null},{\"showContent\":\"自取再享0.9折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":8,\"style\":null}],\"icoType\":3,\"saStyle\":null,\"saImg\":null,\"saType\":null,\"saKey\":null,\"activityTag\":null,\"adPosition\":null,\"isFastFood\":null,\"similarScore\":null,\"similarScoreBD\":null,\"deliveryAndStatus\":null,\"deliveryAndStatusDesc\":null,\"shopMonthlySales\":null,\"averagePurchase\":null,\"sendMoneyDiscount\":null,\"shopBottomIconUrl\":null,\"bought\":null,\"shopFeatureList\":null,\"firstOrderDelivery\":null,\"debugInfo\":{},\"customSort\":null,\"cpcScore\":null,\"shopAdJson\":null,\"distanceLinear\":null,\"inShopTop\":null,\"realTimeSensor\":null,\"memberDiscount\":null,\"memberRedPacketPrice\":null,\"predictDeliveryTimeStr\":null,\"userDistanceD\":631.3979527780708,\"recommendShopId\":1733,\"sortScore\":421.63},{\"shopId\":6256,\"address\":\"中华人民共和国浙江省杭州市310051星巴克\",\"shopName\":\"伏见桃山【奶茶店】coffee-折帽\",\"shopNameEn\":null,\"bgImage\":null,\"shopBgImgEn\":null,\"shopImg\":\"https://static.hungrypanda.global/panda/1635129158993e404dddb53df404c82a813eff18cee84.png\",\"shopStatus\":0,\"onlinePay\":2,\"praiseAverage\":\"4.6\",\"overtime\":null,\"punctuality\":null,\"pandaSend\":0,\"firstOrderDiscounts\":100,\"firstOrderDiscountsStr\":null,\"startSendMoney\":0,\"startSendMoneyStr\":null,\"sendMoney\":540,\"sendMoneyStr\":\"¥5.40\",\"distance\":\"0.32km\",\"time\":null,\"country\":\"中国\",\"shopLabelUrl\":\"http://static.hungrypanda.global/panda/16044792339259b2f8a922855481bb1af964dc2fc451f.png\",\"newShopLabelUrl\":\"\",\"shopBorderUrl\":null,\"takeRateStr\":\"9\",\"shopFirstDiscount\":0,\"preorderClosedSupport\":0,\"deliveryFarawayType\":null,\"productDiscountTagDesc\":\"\",\"memberShopPrice\":1112,\"recommendSort\":2059,\"shopLogo\":\"https://static.hungrypanda.global/panda/1635129157770586e66f29e884c3397f86e4ccf511a25.png?x-oss-process=style/shop_logo\",\"pcShopImg\":null,\"pcShopImgBg\":null,\"shopLabel\":0,\"isUserPack\":1,\"takeOutable\":1,\"makeTime\":35,\"deliveryType\":1,\"shopType\":0,\"perCapitaConsume\":0,\"isDel\":null,\"deliveryRange\":1125500,\"showShopEvaluation\":1,\"longitude\":\"120.22004\",\"latitude\":\"30.203527\",\"fullSubList\":[{\"fullSubId\":null,\"fullSubName\":\"满20减2\",\"shopId\":null,\"full\":2000,\"fullStr\":\"20\",\"sub\":200,\"subStr\":\"2\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"}],\"evaluation\":\"加码折扣和帽子\",\"distanceD\":323.7453988046512,\"deliveryPostcodeInfo\":null,\"newShopLabelStr\":\"\",\"discountSkuNum\":0,\"indexListLabel\":\"营销标签1111\",\"redPacketRate\":\"\",\"redPacketPriceTotal\":0,\"firstVoucherGoodsPrice\":0,\"firstVoucherSalePrice\":0,\"minNewUserSkuPrice\":-1,\"isSupermarket\":0,\"shopBgimageCN\":null,\"recommendSortEasi\":2059,\"exclusive\":0,\"newShopTag\":\"\",\"praiseAverageNew\":\"4.6\",\"sendMoneyMsg\":\"配送¥5.4起\",\"hideBusinessName\":1,\"shopPacketPricePriorityRedPacketAmount\":0,\"shopPacketRatePriorityRedPacketRate\":0.0,\"shopPacketPricePriorityRedPacketAmountEn\":0,\"shopPacketRatePriorityRedPacketRateEn\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThr\":0,\"shopPacketRatePriorityRedPacketRateNoThr\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThrEn\":0,\"shopPacketRatePriorityRedPacketRateNoThrEn\":0.0,\"shopPacketPriceSubsidy\":null,\"shopPacketPriceNoThrSubsidy\":null,\"shopPacketRateSubsidy\":null,\"shopPacketRateNoThrSubsidy\":null,\"memberPacketPriceSubsidy\":null,\"memberPacketPriceToRateSubsidy\":null,\"newChannelLabelUrl\":\"\",\"supportedLanguageCodes\":[\"EN\",\"CN\"],\"hotSalesProductIdList\":[7899146,7899121,7899441,7899424,7899026,7899120,7899126,7899119,7899125],\"tagType\":0,\"fullSubInfo\":null,\"distanceFarawayStart\":null,\"distanceFarawayEnd\":null,\"maxDeliveryRangeMeter\":null,\"easiMaxDeliveryRangeMeter\":null,\"overAffordOrderLimit\":null,\"searchMatch\":null,\"downGrade\":null,\"cancelOrderDegrade\":null,\"memberDeliveryDiscount\":0,\"productDiscountPromoteType\":null,\"shopRank\":\"滨江区甜点饮品榜第2名\",\"rankNo\":2,\"tobaccoShop\":1,\"specialProductIdList\":null,\"recProductType\":null,\"preorderOpenType\":1,\"fixedTagSts\":0,\"fixedTagFilter\":0,\"repeatCustomer\":null,\"specialPromote\":null,\"dineInSame\":null,\"mealsOnTime\":null,\"marketingProductIdList\":[1195282,1197513,7899024,7899026,7899119,7899120],\"activityTagType\":null,\"pandaLeagueTag\":0,\"pandaLeagueFilter\":0,\"shopPacketAmountPandaLeague\":0,\"shopPacketDiscountPandaLeague\":0,\"shopPacketAmountPandaLeagueEn\":0,\"shopPacketDiscountPandaLeagueEn\":0,\"subsidyPrice\":null,\"subsidyRateMaxPrice\":null,\"subsidyRate\":null,\"offlineMakeUpRate\":null,\"discountTakeSelf\":null,\"weightValue\":2059.32,\"predictDeliveryTime\":null,\"isShowDelivery\":1,\"moduleSort\":null,\"listPostCodes\":null,\"prices\":null,\"shopIsTakeType\":1,\"outOfRange\":null,\"shopMarketTagDesc\":null,\"marketShowType\":null,\"moduleActivityShopId\":null,\"shopNameSort\":null,\"city\":\"杭州市\",\"products\":null,\"productVOList\":null,\"memberShopPriceTotal\":1112,\"score\":\"NaN\",\"lastDeliveryPrice\":null,\"showShopEvaluationNum\":null,\"shopBgimageEn\":null,\"shopBgimageCn\":null,\"createTime\":0,\"updateTime\":0,\"isCollect\":0,\"productList\":null,\"promotionLabel\":null,\"promotionFullSub\":null,\"currency\":null,\"merchantCategoryId\":1254,\"merchantCategoryName\":\"水果生鲜\",\"largeRedPacketList\":null,\"redPacketList\":null,\"shippingDiscount\":null,\"averageFullSub\":null,\"discountOn\":null,\"discountRate\":null,\"saId\":null,\"showAd\":null,\"deliveryFence\":null,\"predicts\":\"{\\\"predictHandler\\\":\\\"default\\\",\\\"score\\\":2059.32,\\\"time\\\":1709877221434,\\\"version\\\":1}\",\"deliveryStatus\":1,\"shopPromoteList\":[{\"showContent\":\"独享|全店9.5折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":3,\"style\":null},{\"showContent\":\"满¥20减¥2\",\"subsidyContent\":null,\"showContentList\":null,\"type\":2,\"style\":null},{\"showContent\":\"新客减¥1\",\"subsidyContent\":null,\"showContentList\":null,\"type\":1,\"style\":null},{\"showContent\":\"自取再享9折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":8,\"style\":null}],\"icoType\":3,\"saStyle\":null,\"saImg\":null,\"saType\":null,\"saKey\":null,\"activityTag\":null,\"adPosition\":null,\"isFastFood\":null,\"similarScore\":null,\"similarScoreBD\":null,\"deliveryAndStatus\":null,\"deliveryAndStatusDesc\":null,\"shopMonthlySales\":null,\"averagePurchase\":null,\"sendMoneyDiscount\":null,\"shopBottomIconUrl\":null,\"bought\":null,\"shopFeatureList\":null,\"firstOrderDelivery\":null,\"debugInfo\":{},\"customSort\":null,\"cpcScore\":null,\"shopAdJson\":null,\"distanceLinear\":null,\"inShopTop\":null,\"realTimeSensor\":null,\"memberDiscount\":null,\"memberRedPacketPrice\":null,\"predictDeliveryTimeStr\":null,\"userDistanceD\":323.7453988046512,\"recommendShopId\":6256,\"sortScore\":2059.32},{\"shopId\":9396,\"address\":\"1214JianghuiRd,BinJiangQu,HangZhouShi,ZheJiangSheng,中華人民共和国311200\",\"shopName\":\"烤布蕾-算法专用\",\"shopNameEn\":null,\"bgImage\":null,\"shopBgImgEn\":null,\"shopImg\":\"https://static.hungrypanda.global/panda/1646727541122ff7810cea240476486c162b8ebdbc7a5.png\",\"shopStatus\":0,\"onlinePay\":2,\"praiseAverage\":\"5.0\",\"overtime\":null,\"punctuality\":null,\"pandaSend\":0,\"firstOrderDiscounts\":0,\"firstOrderDiscountsStr\":null,\"startSendMoney\":100,\"startSendMoneyStr\":null,\"sendMoney\":200,\"sendMoneyStr\":\"¥2.00\",\"distance\":\"0.7km\",\"time\":null,\"country\":\"中国\",\"shopLabelUrl\":\"\",\"newShopLabelUrl\":\"\",\"shopBorderUrl\":null,\"takeRateStr\":\"9\",\"shopFirstDiscount\":0,\"preorderClosedSupport\":0,\"deliveryFarawayType\":null,\"productDiscountTagDesc\":\"低至2折\",\"memberShopPrice\":0,\"recommendSort\":2086,\"shopLogo\":\"https://static.hungrypanda.global/panda/1646727539996a29317ca7efd420eb07cf60bd3106d84.png?x-oss-process=style/shop_logo\",\"pcShopImg\":null,\"pcShopImgBg\":null,\"shopLabel\":0,\"isUserPack\":1,\"takeOutable\":1,\"makeTime\":20,\"deliveryType\":1,\"shopType\":0,\"perCapitaConsume\":0,\"isDel\":null,\"deliveryRange\":1154000,\"showShopEvaluation\":0,\"longitude\":\"120.21093\",\"latitude\":\"30.199545\",\"fullSubList\":null,\"evaluation\":\"好吃不贵经典实惠\",\"distanceD\":698.3175906383644,\"deliveryPostcodeInfo\":null,\"newShopLabelStr\":\"新店尝鲜\",\"discountSkuNum\":20,\"indexListLabel\":\"22\",\"redPacketRate\":\"\",\"redPacketPriceTotal\":0,\"firstVoucherGoodsPrice\":0,\"firstVoucherSalePrice\":0,\"minNewUserSkuPrice\":-1,\"isSupermarket\":0,\"shopBgimageCN\":null,\"recommendSortEasi\":2086,\"exclusive\":0,\"newShopTag\":\"\",\"praiseAverageNew\":\"\",\"sendMoneyMsg\":\"配送¥2起\",\"hideBusinessName\":0,\"shopPacketPricePriorityRedPacketAmount\":0,\"shopPacketRatePriorityRedPacketRate\":0.0,\"shopPacketPricePriorityRedPacketAmountEn\":0,\"shopPacketRatePriorityRedPacketRateEn\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThr\":0,\"shopPacketRatePriorityRedPacketRateNoThr\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThrEn\":0,\"shopPacketRatePriorityRedPacketRateNoThrEn\":0.0,\"shopPacketPriceSubsidy\":null,\"shopPacketPriceNoThrSubsidy\":null,\"shopPacketRateSubsidy\":null,\"shopPacketRateNoThrSubsidy\":null,\"memberPacketPriceSubsidy\":null,\"memberPacketPriceToRateSubsidy\":null,\"newChannelLabelUrl\":\"\",\"supportedLanguageCodes\":[\"EN\",\"CN\"],\"hotSalesProductIdList\":null,\"tagType\":0,\"fullSubInfo\":null,\"distanceFarawayStart\":null,\"distanceFarawayEnd\":null,\"maxDeliveryRangeMeter\":null,\"easiMaxDeliveryRangeMeter\":null,\"overAffordOrderLimit\":null,\"searchMatch\":null,\"downGrade\":null,\"cancelOrderDegrade\":null,\"memberDeliveryDiscount\":0,\"productDiscountPromoteType\":null,\"shopRank\":\"\",\"rankNo\":null,\"tobaccoShop\":0,\"specialProductIdList\":null,\"recProductType\":null,\"preorderOpenType\":1,\"fixedTagSts\":0,\"fixedTagFilter\":0,\"repeatCustomer\":null,\"specialPromote\":null,\"dineInSame\":null,\"mealsOnTime\":null,\"marketingProductIdList\":[7960913,7960915,7960916,7960919,7960920,7960921],\"activityTagType\":null,\"pandaLeagueTag\":0,\"pandaLeagueFilter\":0,\"shopPacketAmountPandaLeague\":0,\"shopPacketDiscountPandaLeague\":0,\"shopPacketAmountPandaLeagueEn\":0,\"shopPacketDiscountPandaLeagueEn\":0,\"subsidyPrice\":null,\"subsidyRateMaxPrice\":null,\"subsidyRate\":null,\"offlineMakeUpRate\":null,\"discountTakeSelf\":null,\"weightValue\":2086.7,\"predictDeliveryTime\":null,\"isShowDelivery\":1,\"moduleSort\":null,\"listPostCodes\":null,\"prices\":null,\"shopIsTakeType\":1,\"outOfRange\":null,\"shopMarketTagDesc\":null,\"marketShowType\":null,\"moduleActivityShopId\":null,\"shopNameSort\":null,\"city\":\"杭州市\",\"products\":null,\"productVOList\":null,\"memberShopPriceTotal\":0,\"score\":\"NaN\",\"lastDeliveryPrice\":null,\"showShopEvaluationNum\":null,\"shopBgimageEn\":null,\"shopBgimageCn\":null,\"createTime\":0,\"updateTime\":0,\"isCollect\":0,\"productList\":null,\"promotionLabel\":null,\"promotionFullSub\":null,\"currency\":null,\"merchantCategoryId\":1252,\"merchantCategoryName\":\"超市便利\",\"largeRedPacketList\":null,\"redPacketList\":null,\"shippingDiscount\":null,\"averageFullSub\":null,\"discountOn\":null,\"discountRate\":null,\"saId\":null,\"showAd\":null,\"deliveryFence\":null,\"predicts\":\"{\\\"predictHandler\\\":\\\"default\\\",\\\"score\\\":2086.7,\\\"time\\\":1709877221434,\\\"version\\\":1}\",\"deliveryStatus\":1,\"shopPromoteList\":[{\"showContent\":\"低至2折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":3,\"style\":null},{\"showContent\":\"自取再享9折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":8,\"style\":null}],\"icoType\":3,\"saStyle\":null,\"saImg\":null,\"saType\":null,\"saKey\":null,\"activityTag\":null,\"adPosition\":null,\"isFastFood\":null,\"similarScore\":null,\"similarScoreBD\":null,\"deliveryAndStatus\":null,\"deliveryAndStatusDesc\":null,\"shopMonthlySales\":null,\"averagePurchase\":null,\"sendMoneyDiscount\":null,\"shopBottomIconUrl\":null,\"bought\":null,\"shopFeatureList\":null,\"firstOrderDelivery\":null,\"debugInfo\":{},\"customSort\":null,\"cpcScore\":null,\"shopAdJson\":null,\"distanceLinear\":null,\"inShopTop\":null,\"realTimeSensor\":null,\"memberDiscount\":null,\"memberRedPacketPrice\":null,\"predictDeliveryTimeStr\":null,\"userDistanceD\":698.3175906383644,\"recommendShopId\":9396,\"sortScore\":2086.7},{\"shopId\":3936,\"address\":\"中国浙江省杭州市滨江区春晓路与月明路交叉口100号邮政编码:311200\",\"shopName\":\"hww测试店铺(勿改）bocai\",\"shopNameEn\":null,\"bgImage\":null,\"shopBgImgEn\":null,\"shopImg\":\"https://static.hungrypanda.global/panda/161364218988668405e3761a140beb387ef8b41e41d4d.png\",\"shopStatus\":1,\"onlinePay\":2,\"praiseAverage\":\"4.3\",\"overtime\":null,\"punctuality\":null,\"pandaSend\":0,\"firstOrderDiscounts\":0,\"firstOrderDiscountsStr\":null,\"startSendMoney\":200,\"startSendMoneyStr\":null,\"sendMoney\":100,\"sendMoneyStr\":\"¥1.00\",\"distance\":\"0.58km\",\"time\":null,\"country\":\"中国\",\"shopLabelUrl\":\"\",\"newShopLabelUrl\":\"\",\"shopBorderUrl\":null,\"takeRateStr\":\"9\",\"shopFirstDiscount\":0,\"preorderClosedSupport\":1,\"deliveryFarawayType\":null,\"productDiscountTagDesc\":\"\",\"memberShopPrice\":0,\"recommendSort\":1964,\"shopLogo\":\"https://static.hungrypanda.global/panda/16136421889325759e49793bd4703a5113283a1b25623.png?x-oss-process=style/shop_logo\",\"pcShopImg\":null,\"pcShopImgBg\":null,\"shopLabel\":0,\"isUserPack\":1,\"takeOutable\":1,\"makeTime\":5,\"deliveryType\":0,\"shopType\":0,\"perCapitaConsume\":0,\"isDel\":null,\"deliveryRange\":2124000,\"showShopEvaluation\":1,\"longitude\":\"120.21064\",\"latitude\":\"30.203483\",\"fullSubList\":[{\"fullSubId\":null,\"fullSubName\":\"满1000减500\",\"shopId\":null,\"full\":100000,\"fullStr\":\"1000\",\"sub\":50000,\"subStr\":\"500\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"},{\"fullSubId\":null,\"fullSubName\":\"满100减46\",\"shopId\":null,\"full\":10000,\"fullStr\":\"100\",\"sub\":4600,\"subStr\":\"46\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"},{\"fullSubId\":null,\"fullSubName\":\"满50减12\",\"shopId\":null,\"full\":5000,\"fullStr\":\"50\",\"sub\":1200,\"subStr\":\"12\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"},{\"fullSubId\":null,\"fullSubName\":\"满30减6\",\"shopId\":null,\"full\":3000,\"fullStr\":\"30\",\"sub\":600,\"subStr\":\"6\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"country\":null,\"merFull\":null,\"merSub\":null,\"fullSubMerchant\":null,\"fullSubPlatform\":null,\"merFullSubName\":null,\"languageCode\":null,\"lang\":\"CN\"}],\"evaluation\":\"verygood\",\"distanceD\":580.074233237511,\"deliveryPostcodeInfo\":null,\"newShopLabelStr\":\"\",\"discountSkuNum\":0,\"indexListLabel\":\"\",\"redPacketRate\":\"\",\"redPacketPriceTotal\":0,\"firstVoucherGoodsPrice\":0,\"firstVoucherSalePrice\":0,\"minNewUserSkuPrice\":-1,\"isSupermarket\":0,\"shopBgimageCN\":null,\"recommendSortEasi\":1964,\"exclusive\":0,\"newShopTag\":\"\",\"praiseAverageNew\":\"4.3\",\"sendMoneyMsg\":\"配送¥1起\",\"hideBusinessName\":1,\"shopPacketPricePriorityRedPacketAmount\":0,\"shopPacketRatePriorityRedPacketRate\":0.0,\"shopPacketPricePriorityRedPacketAmountEn\":0,\"shopPacketRatePriorityRedPacketRateEn\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThr\":0,\"shopPacketRatePriorityRedPacketRateNoThr\":0.0,\"shopPacketPricePriorityRedPacketAmountNoThrEn\":0,\"shopPacketRatePriorityRedPacketRateNoThrEn\":0.0,\"shopPacketPriceSubsidy\":null,\"shopPacketPriceNoThrSubsidy\":null,\"shopPacketRateSubsidy\":null,\"shopPacketRateNoThrSubsidy\":null,\"memberPacketPriceSubsidy\":null,\"memberPacketPriceToRateSubsidy\":null,\"newChannelLabelUrl\":\"\",\"supportedLanguageCodes\":[\"EN\",\"CN\"],\"hotSalesProductIdList\":null,\"tagType\":0,\"fullSubInfo\":null,\"distanceFarawayStart\":null,\"distanceFarawayEnd\":null,\"maxDeliveryRangeMeter\":null,\"easiMaxDeliveryRangeMeter\":null,\"overAffordOrderLimit\":null,\"searchMatch\":null,\"downGrade\":null,\"cancelOrderDegrade\":null,\"memberDeliveryDiscount\":0,\"productDiscountPromoteType\":null,\"shopRank\":\"\",\"rankNo\":null,\"tobaccoShop\":0,\"specialProductIdList\":null,\"recProductType\":null,\"preorderOpenType\":2,\"fixedTagSts\":0,\"fixedTagFilter\":0,\"repeatCustomer\":null,\"specialPromote\":null,\"dineInSame\":null,\"mealsOnTime\":null,\"marketingProductIdList\":[7947250,7947251,7947684,7947687],\"activityTagType\":null,\"pandaLeagueTag\":0,\"pandaLeagueFilter\":0,\"shopPacketAmountPandaLeague\":0,\"shopPacketDiscountPandaLeague\":0,\"shopPacketAmountPandaLeagueEn\":0,\"shopPacketDiscountPandaLeagueEn\":0,\"subsidyPrice\":null,\"subsidyRateMaxPrice\":null,\"subsidyRate\":null,\"offlineMakeUpRate\":null,\"discountTakeSelf\":null,\"weightValue\":1964.58,\"predictDeliveryTime\":null,\"isShowDelivery\":0,\"moduleSort\":null,\"listPostCodes\":null,\"prices\":null,\"shopIsTakeType\":1,\"outOfRange\":null,\"shopMarketTagDesc\":null,\"marketShowType\":null,\"moduleActivityShopId\":null,\"shopNameSort\":null,\"city\":\"杭州市\",\"products\":null,\"productVOList\":null,\"memberShopPriceTotal\":0,\"score\":\"NaN\",\"lastDeliveryPrice\":null,\"showShopEvaluationNum\":null,\"shopBgimageEn\":null,\"shopBgimageCn\":null,\"createTime\":0,\"updateTime\":0,\"isCollect\":0,\"productList\":null,\"promotionLabel\":null,\"promotionFullSub\":null,\"currency\":null,\"merchantCategoryId\":1254,\"merchantCategoryName\":\"水果生鲜\",\"largeRedPacketList\":null,\"redPacketList\":null,\"shippingDiscount\":null,\"averageFullSub\":null,\"discountOn\":null,\"discountRate\":null,\"saId\":null,\"showAd\":null,\"deliveryFence\":null,\"predicts\":\"{\\\"predictHandler\\\":\\\"default\\\",\\\"score\\\":1964.58,\\\"time\\\":1709877221434,\\\"version\\\":1}\",\"deliveryStatus\":1,\"shopPromoteList\":[{\"showContent\":\"满减最高减¥500\",\"subsidyContent\":null,\"showContentList\":null,\"type\":2,\"style\":null},{\"showContent\":\"自取再享9折\",\"subsidyContent\":null,\"showContentList\":null,\"type\":8,\"style\":null}],\"icoType\":3,\"saStyle\":null,\"saImg\":null,\"saType\":null,\"saKey\":null,\"activityTag\":null,\"adPosition\":null,\"isFastFood\":null,\"similarScore\":null,\"similarScoreBD\":null,\"deliveryAndStatus\":null,\"deliveryAndStatusDesc\":null,\"shopMonthlySales\":null,\"averagePurchase\":null,\"sendMoneyDiscount\":null,\"shopBottomIconUrl\":null,\"bought\":null,\"shopFeatureList\":null,\"firstOrderDelivery\":null,\"debugInfo\":{},\"customSort\":null,\"cpcScore\":null,\"shopAdJson\":null,\"distanceLinear\":null,\"inShopTop\":null,\"realTimeSensor\":null,\"memberDiscount\":null,\"memberRedPacketPrice\":null,\"predictDeliveryTimeStr\":null,\"userDistanceD\":580.074233237511,\"recommendShopId\":3936,\"sortScore\":1964.58}],\"totalShopNum\":4,\"marketCategories\":[],\"mapRadius\":900000,\"showShopNum\":20,\"shopCardType\":null,\"shopCardVersion\":null,\"searchCardVersion\":null},\"currency\":\"¥\",\"success\":true,\"nowTime\":1709877221441,\"queryList\":{\"1\":\"hp_master:SELECTa.business_category_idFROMhp_market_category_business_category_relationaLEFTJOINhp_business_categorybONa.business_category_id=b.business_category_idWHEREa.is_deleted=0ANDb.STATUS=1ANDb.category_level=2ANDa.market_category_id=51\",\"2\":\"hp_master:SELECTimd.index_module_detail_id,imd.module_activity_id,im.module_name,imd.content,imd.img_url,imd.sort,imd.title,imd.type,imd.url,imd.postcode,imd.parent_title,imd.sub_title,imd.visible_type,im.is_auto_associate_city,imd.dimension,im.operate_area_id,imd.is_show_bubble,imd.bubble_content,imd.ad_type,imd.absolute_sort,imd.ad_id,imd.icon_color,imd.img_url_new,imd.jump_dataFROMindex_module_detailimdLEFTJOINindex_moduleimONim.index_module_id=imd.index_module_idWHEREim.city_name='杭州市'ANDim.status=1ANDim.operate_area_id=0ANDim.visible_typein(0,2)ANDimd.visible_typein(0,2)ANDimd.sts=1ANDim.`status`=1ANDim.module_type=24ANDim.language_code='CN'orderbyimd.sortDESC,imd.create_timedesc\",\"3\":\"hp_master:selectrec_id,index_detail_id,start_time,end_time,is_delfromhp_index_module_detail_timewhereis_del=0andindex_detail_idin(103444838,103444837,103444835,103444839,103444836,103444834)\",\"4\":\"hp_master:selectshop_id,send_moneyfromhp_shop_search_middlewhereshop_idin(1166,1230,1281,1283,1310,1313,1677,1679,1733,3875,3879,3891,3894,3936,4482,4521,4525,6067,6070,6153,6170,6205,6223,6228,6256,6264,9304,9396,9434,9956,10009,10030,10058,10061,10149,10150,10151,10177,10180,10204,10223,10232,1000013,1000081,1000117,1000118,1000120,1000125,1000154,1000169,1000171,1000172,1000173,1000174,1000217,1000221,1000222,1000254,1000262,1000263,1000271,1000279,1000280,1000289,1000295,1000328,1000330,1000332,1000345,1000347,1000358,18076160,39522978,98739065,157291673,166955977,204143711,225919124,267698424,272722680,282061926,298113676,315049912,342865116,357218618,536235947,540219644,544648068,547778083,651600159,676967779,683849100,754116479,836379232,858213782,868230525,892716498,940785618,948993783)\",\"5\":\"hp_master:SELECTa.shop_id,b.discount_sn,b.date_type,b.day_limit,b.time_limit,b.time_slot_type,b.crowd,c.sub_state,c.break_type,d.cityFROMhp_delivery_discount_shopaLEFTJOINhp_delivery_discountbONa.discount_sn=b.discount_snLEFTJOINhp_activity_configcONb.activity_sn=c.activity_snLEFTJOINshopdona.shop_id=d.shop_idWHEREb.is_del=0ANDa.is_del=0ANDb.discount_status=1ANDb.start_time<=1709877221398ANDb.end_time>1709877221398ANDa.shop_idIN(1166,1230,1281,1283,1310,1313,1677,1679,1733,3875,3879,3891,3894,3936,4482,4521,4525,6067,6070,6153,6170,6205,6223,6228,6256,6264,9304,9396,9434,9956,10009,10030,10058,10061,10149,10150,10151,10177,10180,10204,10223,10232,1000013,1000081,1000117,1000118,1000120,1000125,1000154,1000169,1000171,1000172,1000173,1000174,1000217,1000221,1000222,1000254,1000262,1000263,1000271,1000279,1000280,1000289,1000295,1000328,1000330,1000332,1000345,1000347,1000358,18076160,39522978,98739065,157291673,166955977,204143711,225919124,267698424,272722680,282061926,298113676,315049912,342865116,357218618,536235947,540219644,544648068,547778083,651600159,676967779,683849100,754116479,836379232,858213782,868230525,892716498,940785618,948993783)AND1709877221398betweena.start_timeanda.end_time\",\"6\":\"hp_master:SELECTdiscount_snasdiscountSn,GROUP_CONCAT(language_code)languagesFROMhp_delivery_discount_language_codeWHEREis_del=0anddiscount_snIN('94fs0gsN3T','13pxyFD3mc')GROUPBYdiscount_sn\",\"7\":\"hp_master:SELECTdiscount_snasdiscountSn,GROUP_CONCAT(platform_type)platformsFROMhp_delivery_discount_platformWHEREis_del=0anddiscount_snIN('94fs0gsN3T','13pxyFD3mc')GROUPBYdiscount_sn\",\"8\":\"hp_master:SELECTdiscount_sn,shop_id,bear_type,merchant_bear_price,reduce_price,distance_start,distance_endFROMhp_delivery_discount_distance_ruleWHEREis_del=0anddiscount_snIN('94fs0gsN3T','13pxyFD3mc')andshop_idin(0,1281,1283,6153,267698424,1000222,1000221,18076160,1000217,10009,6170,1310,1313,3875,3879,10030,1000254,3891,3894,166955977,676967779,6205,1000271,1000013,1000263,298113676,1000262,10058,10061,683849100,6223,6228,1000280,9304,1000279,536235947,3936,948993783,1000295,1000289,6256,357218618,204143711,6264,315049912,4482,1000332,1000330,547778083,1000328,1677,754116479,1166,1679,1000347,1000345,98739065,1000081,10149,10150,10151,4521,1000358,282061926,4525,858213782,1000125,6067,342865116,9396,6070,1000120,1000118,272722680,1000117,836379232,868230525,10177,940785618,10180,1733,651600159,1230,540219644,1000154,9434,10204,1000174,1000173,1000172,225919124,9956,1000171,1000169,892716498,10223,544648068,10232,39522978,157291673)\",\"9\":\"hp_master:selectcount(1)frommember_entitywhereuser_id=andis_del=0andstatus=1andmember_start_time<1709877221426andmember_end_time>1709877221426\",\"10\":\"hp_master:selectrec_id,user_id,device_id,label_group,label_id,create_time,update_timefromhp_user_labelWHEREdevice_id='d88a89d4913c70bd'andlabel_group='new'\",\"11\":\"hp_slave:selectconfig_key,config_valuefromhp_sys_app_configwhereconfig_key='FIRST_ORDER_DELIVERY_CONFIG'limit1\",\"12\":\"hp_master:SELECT*FROM(SELECTs.shop_id,s.`shop_name`,sbc.`business_category_id`,bc.`business_category_name`,bc.`category_level`,bc.business_type,bc.`parent_business_category_id`,fbc.`business_category_name`AS'parentBusinessCategoryName',fbc.`category_level`AS'parentCategoryLevel',fbc.business_typeAS'parentBusiness_type'FROMshopsLEFTJOINhp_shop_business_categorysbcONsbc.shop_id=s.shop_idANDsbc.status=1ANDsbc.category_type=0LEFTJOIN`hp_business_category`bcONsbc.business_category_id=bc.business_category_idANDbc.status=1LEFTJOIN`hp_business_category`fbcONfbc.business_category_id=bc.parent_business_category_idWHERE1=1andsbc.business_category_idin(1254,1254,1252,1254)ands.shop_idin(1733,6256,9396,3936)ORDERBYsbc.`create_time`DESC)t1GROUPBYshop_id\",\"13\":\"hp_master:SELECText.*fromcity_function_configextleftjoincity_configcononcon.city_id=ext.city_idwherecon.is_del=0andext.is_del=0ANDcon.city='杭州市'ANDext.lang='CN'ANDext.`type`=0\"}}";
        Boolean jsonFormat = JSONUtils.isJSONFormat(json);
        assertTrue(jsonFormat);
    }

    @Test
    @DisplayName("测试 Jackson 注解 @JsonProperty 的属性序列化和反序列化能正确取到值")
    void testJacksonAnnotationOfJsonProperty() {
        Person person = new Person();
        person.setProductId(100L);

        // String s = JSONUtils.toJSONString(person);   // UnsupportedOperationException, 使用 Fastjson 序列化 Jackson
        String s = JSONUtils.toJSONStringByJackson(person);

        Person person2 = JSONUtils.jsonToObjectByJackson(s, Person.class);
        System.out.println(person2.getProductId());
    }

    @DisplayName("更新JSON字符串中的指定的key的值为某个值，通过JsonPath定位要更新的key")
    @Test
    void testUpdateJsonValueByPath() {
        // 准备测试数据
        String jsonStr = """
                {
                    "store": {
                        "book": [
                            {
                                "title": "Java编程思想",
                                "author": "Bruce Eckel",
                                "price": 99.00,
                                "tags": ["编程", "Java", "经典"],
                                "details": {
                                    "pages": 800,
                                    "publisher": "机械工业出版社"
                                }
                            },
                            {
                                "title": "设计模式",
                                "author": "Erich Gamma",
                                "price": 88.00
                            }
                        ],
                        "location": "北京",
                        "isOpen": true,
                        "rating": 4.5,
                        "categories": ["书店", "咖啡厅"]
                    }
                }
                """;

        // 测试1：更新简单字符串值
        String updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.location", "上海");
        assertTrue(updatedJson.contains("\"location\":\"上海\""));

        // 测试2：更新数字值
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.rating", 5.0);
        assertTrue(updatedJson.contains("\"rating\":5.0"));

        // 测试3：更新布尔值
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.isOpen", false);
        assertTrue(updatedJson.contains("\"isOpen\":false"));

        // 测试4：更新数组中的对象值
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.book[0].price", 199.00);
        assertTrue(updatedJson.contains("\"price\":199.0"));

        // 测试5：更新嵌套对象中的值
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.book[0].details.pages", 1000);
        assertTrue(updatedJson.contains("\"pages\":1000"));

        // 测试6：更新数组中的元素
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.book[0].tags[0]", "计算机");
        assertTrue(updatedJson.contains("\"tags\":[\"计算机\",\"Java\",\"经典\"]"));

        // 测试7：更新整个数组
        String[] newCategories = {"书店", "咖啡厅", "文创"};
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.categories", newCategories);
        assertTrue(updatedJson.contains("\"categories\":[\"书店\",\"咖啡厅\",\"文创\"]"));

        // 测试8：更新不存在的路径
        assertThrows(PathNotFoundException.class, () -> {
            JSONUtils.updateJsonValueByPath(jsonStr, "$.store.book[5].title", "新书");
        });

        // 测试9：更新不存在的嵌套路径
        assertThrows(PathNotFoundException.class, () -> {
            JSONUtils.updateJsonValueByPath(jsonStr, "$.store.book[0].details.isbn", "123456");
        });

        // 测试10：无效的JSON格式
        assertThrows(JSONException.class, () -> {
            JSONUtils.updateJsonValueByPath("invalid json", "$.store.book[0].title", "新书");
        });

        // 测试11：更新为null值
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store.location", null);
        assertTrue(updatedJson.contains("\"location\":null"));

        // 测试12：更新为复杂对象
        String complexObject = """
            {
                "name": "新书店",
                "address": "上海市浦东新区"
            }
            """;
        updatedJson = JSONUtils.updateJsonValueByPath(jsonStr, "$.store", complexObject);
        assertTrue(updatedJson.contains("\"name\":\"新书店\""));
        assertTrue(updatedJson.contains("\"address\":\"上海市浦东新区\""));
    }

    @DisplayName("更新单层结构JSON字符串中指定key的值")
    @Test
    void testUpdateJsonValue() {
        // 准备测试数据
        String jsonStr = """
            {
                "name": "张三",
                "age": 25,
                "isStudent": true,
                "score": 95.5,
                "tags": ["学生", "优秀"],
                "address": null
            }
            """;

        // 测试1：更新字符串值
        String updatedJson = JSONUtils.updateJsonValue(jsonStr, "name", "李四");
        assertTrue(updatedJson.contains("\"name\":\"李四\""));

        // 测试2：更新数字值
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "age", 26);
        assertTrue(updatedJson.contains("\"age\":26"));

        // 测试3：更新布尔值
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "isStudent", false);
        assertTrue(updatedJson.contains("\"isStudent\":false"));

        // 测试4：更新浮点数值
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "score", 98.5);
        assertTrue(updatedJson.contains("\"score\":98.5"));

        // 测试5：更新数组值
        String[] newTags = {"学生", "优秀", "三好学生"};
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "tags", newTags);
        assertTrue(updatedJson.contains("\"tags\":[\"学生\",\"优秀\",\"三好学生\"]"));

        // 测试6：更新为null值
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "name", null);
        assertTrue(updatedJson.contains("\"name\":null"));

        // 测试7：更新不存在的key
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "newKey", "新值");
        assertTrue(updatedJson.contains("\"newKey\":\"新值\""));

        // 测试8：更新为复杂对象
        String complexObject = """
            {
                "city": "北京",
                "street": "朝阳区"
            }
            """;
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "address", complexObject);
        assertTrue(updatedJson.contains("\"city\":\"北京\""));
        assertTrue(updatedJson.contains("\"street\":\"朝阳区\""));

        // 测试9：无效的JSON格式
        assertThrows(JSONException.class, () -> {
            JSONUtils.updateJsonValue("invalid json", "key", "value");
        });

        // 测试10：空JSON对象
        String emptyJson = "{}";
        updatedJson = JSONUtils.updateJsonValue(emptyJson, "key", "value");
        assertTrue(updatedJson.contains("\"key\":\"value\""));

        // 测试11：更新为数字数组
        int[] numbers = {1, 2, 3, 4, 5};
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "numbers", numbers);
        assertTrue(updatedJson.contains("\"numbers\":[1,2,3,4,5]"));

        // 测试12：更新为布尔数组
        boolean[] flags = {true, false, true};
        updatedJson = JSONUtils.updateJsonValue(jsonStr, "flags", flags);
        assertTrue(updatedJson.contains("\"flags\":[true,false,true]"));
    }
}

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Person implements Serializable {
    @JsonProperty("item_id")
    private Long productId;
}
