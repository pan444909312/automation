package com.miller.service.framework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.miller.service.framework.util.JsonUnitUtils.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

class CurlParserTest {

    private static final String POST_CURL = """
            curl -H "Host: app-test.hungrypanda.cn" -H "_pendingsign: _ts1748938114115authorization0bb0d075d767fa89b03da500f770433a" -H "userid: 1398664550" -H "language: CN" -H "_sign: 6a951ab0d1d5403fc09d7ab6104ccee6" -H "user-agent: PandaH/8.61.0 (iPhone; iOS 16.7.11; Scale/3.00) OKPOS" -H "_ts: 1748938114115" -H "portalid: 3" -H "latitude: 30.20118" -H "countrycode: CN" -H "version: 8.61.0" -H "platform: IOS_USER" -H "uniquetoken: 0721CD44-5090-42F5-A0B1-8D2F29B85BF5" -H "longitude: 120.22142" -H "authorization: 0bb0d075d767fa89b03da500f770433a" -H "accept-language: zh-Hans-CN;q=1" -H "regionid: 3" -H "reallongitude: 120.22141" -H "timezoneoffset: -480" -H "reallatitude: 30.20117" -H "apptypeid: 1" -H "testgroup: I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,17,S_H_R_L_TEST_GROUP_7,22,23,29,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS02,XMLM01,RRREC02,ZFBMM01,SSJLY01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM01,XGBFU01,SDDAB01,TCSHW01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,LXCYH01,TCZKB01,JLYHR01,HANLP01" -H "content-type: application/json" -H "accept: */*" -H "_sig: dd6c1833b7eee4e4be164fecc1e50bd727d9957e" -H "hpfcityname: %E6%9D%AD%E5%B7%9E%E5%B8%82" --data-binary "{\\"buildingType\\":\\"1\\",\\"accessCode\\":\\"10010\\",\\"addressRemark\\":\\"备注了啥\\",\\"postcode\\":\\"330292\\",\\"longitude\\":\\"120.22185\\",\\"buildingName\\":\\"星耀中心\\",\\"houseNum\\":\\"101\\",\\"isDefault\\":\\"0\\",\\"addTag\\":1,\\"addressId\\":1398680202,\\"address\\":\\"China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心\\",\\"countryCode\\":\\"86\\",\\"latitude\\":\\"30.20074\\",\\"contacts\\":\\"东东\\",\\"type\\":2,\\"buildingNameExt\\":\\"自动化测试\\",\\"telephone\\":\\"15606690056\\",\\"gender\\":1}" --compressed "https://app-test.hungrypanda.cn/api/app/user/v1/address/edit"
            """;

    private static final String GET_CURL = """
            curl -H "Host: app-test.hungrypanda.cn" -H "_pendingsign: _ts1748937506658authorization0bb0d075d767fa89b03da500f770433a" -H "userid: 1398664550" -H "language: CN" -H "_sign: 5248a6cd0776ac9f664a3de9719f44c5" -H "user-agent: PandaH/8.61.0 (iPhone; iOS 16.7.11; Scale/3.00) OKPOS" -H "_ts: 1748937506658" -H "pageno: 1" -H "portalid: 3" -H "latitude: 30.20118" -H "countrycode: CN" -H "version: 8.61.0" -H "platform: IOS_USER" -H "uniquetoken: 0721CD44-5090-42F5-A0B1-8D2F29B85BF5" -H "longitude: 120.22142" -H "authorization: 0bb0d075d767fa89b03da500f770433a" -H "accept-language: zh-Hans-CN;q=1" -H "regionid: 3" -H "reallongitude: 120.22141" -H "timezoneoffset: -480" -H "reallatitude: 30.20117" -H "apptypeid: 1" -H "testgroup: I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,17,S_H_R_L_TEST_GROUP_7,22,23,29,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,FASTD01,YSDCS02,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,SKXRB01,ABT02,QYTCD01,SMSS02,XMLM01,RRREC02,ZFBMM01,SSJLY01,SPSS01,MRBX01,PLCC01,SXAU01,PAYTO01,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS01,SYSKA02,WLTC01,SPM01,XGBFU01,SDDAB01,TCSHW01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,XDRS01,RDMU01,YHMGD01,NTCZT01,DPCDB01,CZHG01,WLTCN01,ESFI02,ABCS01,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,SKYS01,GGCLA01,MGDD01,YFYHA01,SKYH01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,LXCYH01,TCZKB01,JLYHR01,HANLP01" -H "accept: */*" -H "_sig: 64309145b578db2d70e007872b2ff65dbd585a03" -H "hpfcityname: %E6%9D%AD%E5%B7%9E%E5%B8%82" --compressed "https://app-test.hungrypanda.cn/api/user/delivery/address"
            """;

    private static final String POST_CURL_WITH_PARAM = """
            curl -H "Host: api-cn-f2e-test.hungrypanda.cn" -H "accept: application/json, text/plain, */*" -H "content-type: application/json;charset=utf-8" -H "origin: https://edition-test.hungrypanda.cn" -H "accept-language: zh-CN,zh-Hans;q=0.9" -H "user-agent: Mozilla/5.0 (iPhone; CPU iPhone OS 16_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148" -H "referer: https://edition-test.hungrypanda.cn/" --data-binary "{\\"pm\\":\\"GET\\",\\"ph\\":{\\"testGroup\\":\\"I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP,S_H_R_L_TEST_GROUP_2,22,23,29,30,31,32,NUMBER_MASKING_00,33,34,36,35,40,39,45,49,52,53,55,56,HPF,SKEQ02,PLCC02,SKXRB02,ABCS01,SKYS02,MGDD02,SKYH02,XDRS02,XGBFU02,FASTD01,YSDCS02,IST01,HYBQ01,XRJ01,TJBQ01,HYXBQ01,TJTCX01,YBXS02,CCPRO01,ZDFQ01,ABT02,QYTCD01,SMSS01,XMLM01,RRREC01,ZFBMM01,SSJLY01,SPSS01,MRBX01,SXAU01,PAYTO02,LXTZ01,JQSJ01,SYGB01,JSYXR01,GDJ02,ZTKP01,ZKTS02,RTR01,SYUI01,SWS01,DWC01,HHAB01,YHTX01,TCZT01,XTZA01,QDJS01,XGBSS02,SYSKA01,WLTC01,SPM02,SDDAB01,TCSHW02,ZNYX01,JSYHA01,DPCDA01,DPHD01,YRSZT01,TSRW02,LLQX01,RDMU01,YHMGD01,NTCZT01,DPCDB01,HHSQ03,CZHG01,WLTCN01,ESFI02,DPYGB01,HBCY01,GWCYC01,HYUI01,SKBD02,GGCLA01,YFYHA01,XRSY01,HDMR01,SYMK01,CMRT01,CPYHA01,SKYX01,VOOPT01,YHLL01,YJSDA01,XGSPA01,LXCYH01,TCZKB01,JLYHR02,HANLP01\\",\\"version\\":\\"8.61.0\\",\\"appTypeId\\":\\"1\\",\\"uniqueToken\\":\\"2365086D-71E6-4761-9C6B-75234AEEB0BF\\",\\"authorization\\":\\"196500fd5d1912f2cdba3cbaa3a0cdf9\\",\\"platform\\":\\"WEB_IOS\\",\\"marketChannel\\":\\"\\",\\"language\\":\\"CN\\"},\\"pd\\":{},\\"nv\\":\\"2\\",\\"nt\\":\\"1749015909038\\",\\"nn\\":\\"Ut4tXne1nXg6T9TpxTrCrAr3G\\",\\"nd\\":\\"26db2a3adc70b7a\\"}" --compressed "https://api-cn-f2e-test.hungrypanda.cn/api/user/activity/getActivityInfoWithConfigById?activityId=1517"
            """;
    // 校验顺序
    private static final String POST_CURL_ORDER = """
                    curl -H "Host: api-cn-f2e-test.hungrypanda.cn" -H "content-type: application/json" -H "accept: application/json, text/plain, */*" -H "sec-fetch-site: same-site" -H "accept-language: zh-CN,zh-Hans;q=0.9" -H "sec-fetch-mode: cors" -H "origin: https://voucher-f2e-test.hungrypanda.cn" -H "user-agent: Mozilla/5.0 (iPhone; CPU iPhone OS 16_7_11 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148" -H "referer: https://voucher-f2e-test.hungrypanda.cn/" -H "sec-fetch-dest: empty" --data-binary "{\\"pm\\":\\"POST\\",\\"ph\\":{\\"language\\":\\"CN\\",\\"latitude\\":\\"30.20121\\",\\"longitude\\":\\"120.22138\\",\\"countryCode\\":\\"CN\\",\\"appVersion\\":\\"8.61.0\\",\\"authorization\\":\\"0bb0d075d767fa89b03da500f770433a\\"},\\"pd\\":{\\"categoryIds\\":[],\\"locationIds\\":[],\\"filter\\":{\\"sales\\":{\\"min\\":0},\\"serviceType\\":0},\\"city\\":\\"杭州市\\",\\"sortType\\":-1,\\"pageNum\\":1,\\"pageSize\\":20},\\"nv\\":\\"2\\",\\"nt\\":\\"1749625557109\\",\\"nn\\":\\"kFzsVsf4zE3GMlZrmHUAWKicr\\",\\"nd\\":\\"14e2c3dfbae4b43\\"}" --compressed "https://api-cn-f2e-test.hungrypanda.cn/api/app/user/voucher/channel"
            """;

    @DisplayName("POST请求解析测试")
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

    @DisplayName("测试GET请求")
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

    @DisplayName("测试带参数的CURL请求")
    @Test
    void testPostRequestParsingWithParam() throws Exception {
        CurlParser.ParsedRequest request = CurlParser.parse(POST_CURL_WITH_PARAM);

        assertThat(request.getMethod()).isEqualTo("POST");

        // 验证URI
        assertThat(request.getPath()).isEqualTo("/api/user/activity/getActivityInfoWithConfigById");

        // 验证headers
        Map<String, String> headers = request.getHeaders();
        assertThat(headers.get("Host")).isEqualTo("api-cn-f2e-test.hungrypanda.cn");
        assertThat(headers.get("content-type")).isEqualTo("application/json;charset=utf-8");


        // 验证body
        assertThat(request.getBody()).isNotNull();
        assertThat(request.getBody().contains("\"pm\":\"GET\"")).isTrue();


        // 验证params (POST请求通常没有query参数)
        assertTrue(request.getParams().containsKey("activityId"));

        System.out.println(request.getMethod());
        System.out.println(request.getParams());
        System.out.println(request.getBody());
        System.out.println(request.getHeaders());
        System.out.println(request.getUri());
        System.out.println(request.getPath());
    }


    @DisplayName("测试带参数的CURL请求")
    @Test
    void testQueryParamsParsing() throws Exception {
        String queryCurl = "curl 'http://example.com/search?q=java&page=2'";
        CurlParser.ParsedRequest request = CurlParser.parse(queryCurl);

        Map<String, String> params = request.getParams();
        assertEquals(2, params.size());
        assertEquals("java", params.get("q"));
        assertEquals("2", params.get("page"));
    }

    @DisplayName("测试方法自动检测")
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

    @DisplayName("测试HTTP头解析")
    @Test
    void testHeaderParsing() throws Exception {
        String headerCurl = "curl -H 'Content-Type: application/json' -H 'Authorization: Bearer token' 'http://example.com'";
        CurlParser.ParsedRequest request = CurlParser.parse(headerCurl);

        Map<String, String> headers = request.getHeaders();
        assertEquals(2, headers.size());
        assertEquals("application/json", headers.get("Content-Type"));
        assertEquals("Bearer token", headers.get("Authorization"));
    }

    @DisplayName("测试POST请求的表单数据")
    @Test
    void testFormDataParsing() throws Exception {
        String formDataCurl = "curl -X POST 'http://example.com/form' " + "-H 'Content-Type: multipart/form-data' " + "-F 'username=testuser' " + "-F 'file=@test.txt'";
        CurlParser.ParsedRequest request = CurlParser.parse(formDataCurl);

        assertEquals("POST", request.getMethod());
        assertEquals("multipart/form-data", request.getHeaders().get("Content-Type"));
        // 注意：-F 参数目前作为普通body处理
//        assertTrue(request.getBody().contains("username=testuser"));
//        assertTrue(request.getBody().contains("file=@test.txt"));
    }

    @DisplayName("测试POST请求的参数顺序")
    @Test
    void testPostRequestOrderParsing() throws Exception {
        CurlParser.ParsedRequest request = CurlParser.parse(POST_CURL_ORDER);

        // 验证方法
        assertEquals("POST", request.getMethod());

        // 验证URI和路径
        assertEquals("https://api-cn-f2e-test.hungrypanda.cn/api/app/user/voucher/channel", request.getUri());
        assertEquals("/api/app/user/voucher/channel", request.getPath());

        // 验证headers
        Map<String, String> headers = request.getHeaders();
        assertEquals("api-cn-f2e-test.hungrypanda.cn", headers.get("Host"));
        assertEquals("application/json", headers.get("content-type"));
        assertEquals("application/json, text/plain, */*", headers.get("accept"));

        // 验证body中的字段顺序
        String body = request.getBody();
        assertNotNull(body);

        // 解析body为JSONObject
        JSONObject jsonBody = JSON.parseObject(body, Feature.OrderedField);
        
        // 验证顶层字段顺序
        List<String> topLevelFields = new ArrayList<>(jsonBody.keySet());
        assertArrayEquals(
            new String[]{"pm", "ph", "pd", "nv", "nt", "nn", "nd"},
            topLevelFields.toArray(new String[0])
        );

        // 验证ph对象中的字段顺序
        JSONObject ph = jsonBody.getJSONObject("ph");
        List<String> phFields = new ArrayList<>(ph.keySet());
        assertArrayEquals(
            new String[]{"language", "latitude", "longitude", "countryCode", "appVersion", "authorization"},
            phFields.toArray(new String[0])
        );

        // 验证pd对象中的字段顺序
        JSONObject pd = jsonBody.getJSONObject("pd");
        List<String> pdFields = new ArrayList<>(pd.keySet());
        assertArrayEquals(
            new String[]{"categoryIds", "locationIds", "filter", "city", "sortType", "pageNum", "pageSize"},
            pdFields.toArray(new String[0])
        );

        // 验证filter对象中的字段顺序
        JSONObject filter = pd.getJSONObject("filter");
        List<String> filterFields = new ArrayList<>(filter.keySet());
        assertArrayEquals(
            new String[]{"sales", "serviceType"},
            filterFields.toArray(new String[0])
        );

        // 验证sales对象中的字段顺序
        JSONObject sales = filter.getJSONObject("sales");
        List<String> salesFields = new ArrayList<>(sales.keySet());
        assertArrayEquals(
            new String[]{"min"},
            salesFields.toArray(new String[0])
        );

        // 验证具体字段值
        assertEquals("POST", jsonBody.getString("pm"));
        assertEquals("CN", ph.getString("language"));
        assertEquals("30.20121", ph.getString("latitude"));
        assertEquals("120.22138", ph.getString("longitude"));
        assertEquals("CN", ph.getString("countryCode"));
        assertEquals("8.61.0", ph.getString("appVersion"));
        assertEquals("0bb0d075d767fa89b03da500f770433a", ph.getString("authorization"));
        
        assertTrue(pd.getJSONArray("categoryIds").isEmpty());
        assertTrue(pd.getJSONArray("locationIds").isEmpty());
        assertEquals("杭州市", pd.getString("city"));
        assertEquals(-1, pd.getInteger("sortType"));
        assertEquals(1, pd.getInteger("pageNum"));
        assertEquals(20, pd.getInteger("pageSize"));
        
        assertEquals(0, sales.getInteger("min"));
        assertEquals(0, filter.getInteger("serviceType"));
        
        assertEquals("2", jsonBody.getString("nv"));
        assertEquals("1749625557109", jsonBody.getString("nt"));
        assertEquals("kFzsVsf4zE3GMlZrmHUAWKicr", jsonBody.getString("nn"));
        assertEquals("14e2c3dfbae4b43", jsonBody.getString("nd"));
    }
}