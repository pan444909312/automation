package com.miller.service.framework.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URISyntaxException;
import java.util.Map;

class CurlParserTest {

    private static final String POST_CURL = """
            curl -H "Host: app-test.hungrypanda.cn" -H "_pendingsign: _ts1748938114115authorization0bb0d075d767fa89b03da500f770433a" -H "userid: 1398664550" -H "language: CN" -H "_sign: 6a951ab0d1d5403fc09d7ab6104ccee6" -H "user-agent: PandaH/8.61.0 (iPhone; iOS 16.7.11; Scale/3.00) OKPOS" -H "_ts: 1748938114115" -H "portalid: 3" -H "latitude: 30.20118" -H "countrycode: CN" -H "version: 8.61.0" -H "platform: IOS_USER" -H "uniquetoken: 0721CD44-5090-42F5-A0B1-8D2F29B85BF5" -H "longitude: 120.22142" -H "authorization: 0bb0d075d767fa89b03da500f770433a" -H "accept-language: zh-Hans-CN;q=1" -H "regionid: 3" -H "reallongitude: 120.22141" -H "timezoneoffset: -480" -H "reallatitude: 30.20117" -H "apptypeid: 1" -H "testgroup: I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,17,S_H_R_L_TEST_GROUP_7,22,23,29,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS02,XMLM01,RRREC02,ZFBMM01,SSJLY01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM01,XGBFU01,SDDAB01,TCSHW01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,LXCYH01,TCZKB01,JLYHR01,HANLP01" -H "content-type: application/json" -H "accept: */*" -H "_sig: dd6c1833b7eee4e4be164fecc1e50bd727d9957e" -H "hpfcityname: %E6%9D%AD%E5%B7%9E%E5%B8%82" --data-binary "{\\"buildingType\\":\\"1\\",\\"accessCode\\":\\"10010\\",\\"addressRemark\\":\\"备注了啥\\",\\"postcode\\":\\"330292\\",\\"longitude\\":\\"120.22185\\",\\"buildingName\\":\\"星耀中心\\",\\"houseNum\\":\\"101\\",\\"isDefault\\":\\"0\\",\\"addTag\\":1,\\"addressId\\":1398680202,\\"address\\":\\"China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心\\",\\"countryCode\\":\\"86\\",\\"latitude\\":\\"30.20074\\",\\"contacts\\":\\"东东\\",\\"type\\":2,\\"buildingNameExt\\":\\"自动化测试\\",\\"telephone\\":\\"15606690056\\",\\"gender\\":1}" --compressed "https://app-test.hungrypanda.cn/api/app/user/v1/address/edit"
            """;

    private static final String GET_CURL = """
            curl -H "Host: app-test.hungrypanda.cn" -H "_pendingsign: _ts1748937506658authorization0bb0d075d767fa89b03da500f770433a" -H "userid: 1398664550" -H "language: CN" -H "_sign: 5248a6cd0776ac9f664a3de9719f44c5" -H "user-agent: PandaH/8.61.0 (iPhone; iOS 16.7.11; Scale/3.00) OKPOS" -H "_ts: 1748937506658" -H "pageno: 1" -H "portalid: 3" -H "latitude: 30.20118" -H "countrycode: CN" -H "version: 8.61.0" -H "platform: IOS_USER" -H "uniquetoken: 0721CD44-5090-42F5-A0B1-8D2F29B85BF5" -H "longitude: 120.22142" -H "authorization: 0bb0d075d767fa89b03da500f770433a" -H "accept-language: zh-Hans-CN;q=1" -H "regionid: 3" -H "reallongitude: 120.22141" -H "timezoneoffset: -480" -H "reallatitude: 30.20117" -H "apptypeid: 1" -H "testgroup: I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,17,S_H_R_L_TEST_GROUP_7,22,23,29,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS02,XMLM01,RRREC02,ZFBMM01,SSJLY01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM01,XGBFU01,SDDAB01,TCSHW01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,LXCYH01,TCZKB01,JLYHR01,HANLP01" -H "accept: */*" -H "_sig: 64309145b578db2d70e007872b2ff65dbd585a03" -H "hpfcityname: %E6%9D%AD%E5%B7%9E%E5%B8%82" --compressed "https://app-test.hungrypanda.cn/api/user/delivery/address"
            """;
    @Test
    void testPostRequestParsing() throws Exception {
        CurlParser.ParsedRequest request = CurlParser.parse(POST_CURL);

        // 验证方法
        assertEquals("POST", request.getMethod());

        // 验证URI
        assertEquals("https://app-test.hungrypanda.cn/api/app/user/v1/address/edit", request.getUri());

        // 验证headers
        Map<String, String> headers = request.getHeaders();
        assertEquals("app-test.hungrypanda.cn", headers.get("Host"));
        assertEquals("application/json", headers.get("content-type"));
        assertEquals("1398664550", headers.get("userid"));

        // 验证body
        assertNotNull(request.getBody());
        assertTrue(request.getBody().contains("\"buildingName\":\"星耀中心\""));
        assertTrue(request.getBody().contains("\"telephone\":\"15606690056\""));

        // 验证params (POST请求通常没有query参数)
        assertTrue(request.getParams().isEmpty());
        System.out.println(request.getMethod());
        System.out.println(request.getParams());
        System.out.println(request.getBody());
        System.out.println(request.getHeaders());
        System.out.println(request.getUri());
        System.out.println(request.getPath());
    }

    @Test
    void testGetRequestParsing() throws Exception {
        CurlParser.ParsedRequest request = CurlParser.parse(GET_CURL);

        // 验证方法
        assertEquals("GET", request.getMethod());

        // 验证URI
        assertEquals("https://app-test.hungrypanda.cn/api/user/delivery/address", request.getUri());

        // 验证headers
        Map<String, String> headers = request.getHeaders();
        assertEquals("app-test.hungrypanda.cn", headers.get("Host"));
        assertEquals("1", headers.get("pageno"));
        assertEquals("1398664550", headers.get("userid"));

        // 验证params (GET请求可能有query参数)
        assertTrue(request.getParams().isEmpty());

        // GET请求没有body
        assertNull(request.getBody());
        assertTrue(request.getParams().isEmpty());

        System.out.println(request.getMethod());
        System.out.println(request.getParams());
        System.out.println(request.getBody());
        System.out.println(request.getHeaders());
        System.out.println(request.getUri());
        System.out.println(request.getPath());
    }

    @Test
    void testQueryParamsParsing() throws Exception {
        String queryCurl = "curl 'http://example.com/search?q=java&page=2'";
        CurlParser.ParsedRequest request = CurlParser.parse(queryCurl);

        Map<String, String> params = request.getParams();
        assertEquals(2, params.size());
        assertEquals("java", params.get("q"));
        assertEquals("2", params.get("page"));
    }

    @Test
    void testMethodDetection() throws Exception {
        // 显式指定方法
        String putCurl = "curl -X PUT 'http://example.com'";
        CurlParser.ParsedRequest request = CurlParser.parse(putCurl);
        assertEquals("PUT", request.getMethod());

        // 有body时默认为POST
        String postCurl = "curl -d 'data' 'http://example.com'";
        request = CurlParser.parse(postCurl);
        assertEquals("POST", request.getMethod());

        // 无body时默认为GET
        String getCurl = "curl 'http://example.com'";
        request = CurlParser.parse(getCurl);
        assertEquals("GET", request.getMethod());
    }

    @Test
    void testHeaderParsing() throws Exception {
        String headerCurl = "curl -H 'Content-Type: application/json' -H 'Authorization: Bearer token' 'http://example.com'";
        CurlParser.ParsedRequest request = CurlParser.parse(headerCurl);

        Map<String, String> headers = request.getHeaders();
        assertEquals(2, headers.size());
        assertEquals("application/json", headers.get("Content-Type"));
        assertEquals("Bearer token", headers.get("Authorization"));
    }

    @Test
    void testFormDataParsing() throws Exception {
        String formDataCurl = "curl -X POST 'http://example.com/form' " +
                "-H 'Content-Type: multipart/form-data' " +
                "-F 'username=testuser' " +
                "-F 'file=@test.txt'";
        CurlParser.ParsedRequest request = CurlParser.parse(formDataCurl);

        assertEquals("POST", request.getMethod());
        assertEquals("multipart/form-data", request.getHeaders().get("Content-Type"));
        // 注意：-F 参数目前作为普通body处理
//        assertTrue(request.getBody().contains("username=testuser"));
//        assertTrue(request.getBody().contains("file=@test.txt"));
    }
}