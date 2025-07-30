package com.miller.testcase.module.home.en_shoplist_v1;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;

import static com.miller.testcase.utils.TestCaseHelpful.assertThat;

/**
 * cn shoplist v2
 *
 * @author yancancan
 * @version 2.0
 * @since 2025/07/29 18:21:48
 */
@Scenario(
        scenarioID = "01K1AQ3FN2XKGRW8GCXBMFW4FJ", // 自动生成，不要修改
        scenarioName = "cn shoplist v2 demo",
        author = "yancancan@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("cn shoplist v2")
public class Cn_Shoplist_V2_Tests {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
//        不修改实验和经纬度时
//        var responseShopBody = TestCaseHelpful.getShopVOByShopId("703996760", "17700000077", "123456",null,null,null);
//        需要修改实验时，传入新的全量实验
        String testGroupNew="I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,S_H_R_L_TEST_GROUP_8,18,23,29,30,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,LYYL02,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS01,XMLM01,RRREC01,ZFBMM01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA01,WLTC01,SPM02,XGBFU01,SDDAB01,TCSHW01,ZNYX01,JSYHA01,DPCDA01,DPHD01,YRSZT01,SKBQ01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX02,VOOPT01,YHLL01,YJSDA01,XGSPA01,LXCYH01,TCZKB01,XRQSD01,HANLP01,JLYZR01,CDQC01,JGYH01,LLBD01,ZNYXJ01,HYXY01,SSZHY01,TOPBQ01,YRSL01";
        var responseShopBody = TestCaseHelpful.getShopVOByShopId("703996760", "17700000077", "123456", testGroupNew, null,null);
        // 检查响应体不为空
        assertThat(responseShopBody).isNotNull();
        // 检查响应体不为空
//        assertThat(responseShopBody).isNull();
        // 获取并校验 deliveryAndStatus
        Integer deliveryAndStatus = responseShopBody.getInteger("deliveryAndStatus");
        Assertions.assertNotNull(deliveryAndStatus, "deliveryAndStatus 不应为空");
        assertThat(deliveryAndStatus).isEqualTo(4);
    }
} 