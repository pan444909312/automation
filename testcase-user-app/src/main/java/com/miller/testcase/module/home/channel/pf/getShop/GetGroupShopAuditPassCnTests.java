package com.miller.testcase.module.home.channel.pf.getShop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.testcase.utils.TestCaseHelpful.assertThat;

@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GJ" ,
        scenarioName = "品类专题_店铺召回：店铺有效状态-语言版本-中文",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("品类专题_店铺召回：店铺有效状态-语言版本-中文")
public class GetGroupShopAuditPassCnTests {

    @DisplayName("品类专题_店铺召回：店铺有效状态-语言版本-中文")
    @Test
    void shouldReturnSuccessfully() {
        //需要修改实验时，传入新的全量实验
        String testGroupNew="I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,S_H_R_L_TEST_GROUP_6,18,23,29,30,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,XGSPA01,SKYX01,XRQSD01,FASTD01,YSDCS02,IST02,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS02,XMLM01,RRREC02,ZFBMM01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO02,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM02,XGBFU01,SDDAB01,TCSHW01,ZNYX01,JSYHA01,DPCDA01,DPHD01,YRSZT01,SKBQ01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,HHSQ03,CZHG01,WLTCN01,ESFI02,ABCS02,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,VOOPT01,YHLL01,YJSDA01,LXCYH01,TCZKB01,HANLP01,JLYZR01,CDQC01,JGYH01,LLBD01,ZNYXJ01,HYXY01,SSZHY01,TOPBQ01,YRSL01";
        var responseShopBody = TestCaseHelpful.channelPfGetShopVOByShopId(43702417,0,55,0, null, null, testGroupNew, null,null,null,null);
        // 检查响应体为空
        assertThat(responseShopBody).isNotNull();

    }
}
