package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-地图笔记-添加笔记
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP83A6T1HTZ3AB3TRSZW746",
        scenarioName = "骑手app-地图笔记-添加笔记",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 90)
@DisplayName("骑手app-地图笔记-添加笔记")
public class AddNoteTests {

    @DisplayName("添加出入口-步行")
    @Test
    void shouldAddEntranceWalkingNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createEntranceWalkingNoteBody());
    }

    @DisplayName("添加出入口-骑行")
    @Test
    void shouldAddEntranceBikingNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createEntranceBikingNoteBody());
    }

    @DisplayName("添加出入口-不可通行")
    @Test
    void shouldAddEntranceNotAccessibleNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createEntranceNotAccessibleNoteBody());
    }

    @DisplayName("添加外卖存放点-公寓存放点-有人值守")
    @Test
    void shouldAddApartmentDepositMannedNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createApartmentDepositMannedNoteBody());
    }

    @DisplayName("添加外卖存放点-公寓存放点-无人值守")
    @Test
    void shouldAddApartmentDepositUnmannedNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createApartmentDepositUnmannedNoteBody());
    }

    @DisplayName("添加外卖存放点-公寓存放点-其他")
    @Test
    void shouldAddApartmentDepositOtherNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createApartmentDepositOtherNoteBody());
    }

    @DisplayName("添加外卖存放点-写字楼存放点-有人值守")
    @Test
    void shouldAddOfficeBuildingDepositMannedNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOfficeBuildingDepositMannedNoteBody());
    }

    @DisplayName("添加外卖存放点-写字楼存放点-其他")
    @Test
    void shouldAddOfficeBuildingDepositOtherNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOfficeBuildingDepositOtherNoteBody());
    }

    @DisplayName("添加外卖存放点-其他公共可存放点-有人值守")
    @Test
    void shouldAddOtherPublicDepositMannedNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOtherPublicDepositMannedNoteBody());
    }

    @DisplayName("添加外卖存放点-其他公共可存放点-无人值守")
    @Test
    void shouldAddOtherPublicDepositUnmannedNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOtherPublicDepositUnmannedNoteBody());
    }

    @DisplayName("添加外卖存放点-其他公共可存放点-其他")
    @Test
    void shouldAddOtherPublicDepositOtherNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOtherPublicDepositOtherNoteBody());
    }

    @DisplayName("添加路况-交通拥堵-不可通行")
    @Test
    void shouldAddTrafficCongestionNotAccessibleNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficCongestionNotAccessibleNoteBody());
    }

    @DisplayName("添加路况-交通拥堵-缓慢通行")
    @Test
    void shouldAddTrafficCongestionSlowPassageNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficCongestionSlowPassageNoteBody());
    }

    @DisplayName("添加路况-交通拥堵-单车道")
    @Test
    void shouldAddTrafficCongestionSingleLaneNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficCongestionSingleLaneNoteBody());
    }

    @DisplayName("添加路况-交通管制-工作日有管制")
    @Test
    void shouldAddTrafficControlWeekdayNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficControlWeekdayNoteBody());
    }

    @DisplayName("添加路况-交通管制-周末有管制")
    @Test
    void shouldAddTrafficControlWeekendNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficControlWeekendNoteBody());
    }

    @DisplayName("添加路况-交通管制-其他")
    @Test
    void shouldAddTrafficControlOtherNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createTrafficControlOtherNoteBody());
    }

    @DisplayName("添加路况-其他交通路况问题")
    @Test
    void shouldAddOtherTrafficIssuesNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createOtherTrafficIssuesNoteBody());
    }

    @DisplayName("添加楼号")
    @Test
    void shouldAddBuildingNumberNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createBuildingNumberNoteBody());
    }

    @DisplayName("添加商家笔记")
    @Test
    void shouldAddShopNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createShopNoteBody());
    }

    @DisplayName("添加自定义笔记")
    @Test
    void shouldAddCustomNote() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        addMapNote(driverAccessToken, createCustomNoteBody());
    }

    /**
     * 添加地图笔记
     */
    private void addMapNote(String driverAccessToken, String body) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/note/addMapNote";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建出入口-步行笔记请求体
     */
    private String createEntranceWalkingNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.2077156003\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区春晓路392号 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.1983108414\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_ENTRANCE\"," +
                "\"genre\":0,\"optionResp\":{\"markOptionList\":[{\"genre\":0,\"name\":\"步行\"," +
                "\"key\":\"MAP_NOTE_ENTRANCE_SUB_TYPE_WALKING\"}],\"optionTitle\":\"\"},\"name\":\"出入口\"}]}}";
    }

    /**
     * 创建出入口-骑行笔记请求体
     */
    private String createEntranceBikingNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.22488361971675\"," +
                "\"anchorPointAddress\":\"中国杭州市滨江区新州花苑北 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.205691146277793\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"name\":\"骑行\"," +
                "\"genre\":0,\"key\":\"MAP_NOTE_ENTRANCE_SUB_TYPE_BIKING\"}]}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_ENTRANCE\",\"name\":\"出入口\"}]}}";
    }

    /**
     * 创建出入口-不可通行笔记请求体
     */
    private String createEntranceNotAccessibleNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.21550747615017\"," +
                "\"anchorPointAddress\":\"中国杭州市滨江区地铁滨和路站 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.199483884285186\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_ENTRANCE_SUB_TYPE_NOT_ACCESSIBLE\"," +
                "\"name\":\"不可通行\",\"genre\":0}]},\"name\":\"出入口\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_ENTRANCE\"}]}}";
    }

    /**
     * 创建外卖存放点-公寓存放点-有人值守笔记请求体
     */
    private String createApartmentDepositMannedNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.20979234774916\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区滨和路56X5+FVP 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.19823937765946\"," +
                "\"items\":{\"markOptionList\":[{\"name\":\"外卖存放点\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\",\"genre\":0," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_APARTMENT_DEPOSIT\"," +
                "\"genre\":0,\"optionResp\":{\"markOptionList\":[{\"name\":\"有人值守\",\"genre\":0," +
                "\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_MANNED\"}],\"optionTitle\":\"\"}," +
                "\"name\":\"公寓存放点\"}]}}],\"optionTitle\":\"\"}}";
    }

    /**
     * 创建外卖存放点-公寓存放点-无人值守笔记请求体
     */
    private String createApartmentDepositUnmannedNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.20230103214594\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区江南大道268号 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.201064928464557\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\"," +
                "\"genre\":0,\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"name\":\"无人值守\"," +
                "\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_UNMANNED\",\"genre\":0}]}," +
                "\"name\":\"公寓存放点\",\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_APARTMENT_DEPOSIT\"}]}," +
                "\"name\":\"外卖存放点\"}]}}";
    }

    /**
     * 创建外卖存放点-公寓存放点-其他笔记请求体
     */
    private String createApartmentDepositOtherNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.2134995464628\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区滨和路56X7+MFC 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.198929531437578\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"name\":\"外卖存放点\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\",\"genre\":0," +
                "\"optionResp\":{\"markOptionList\":[{\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"genre\":0,\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_OTHER\"," +
                "\"name\":\"其他\"}]},\"genre\":0,\"name\":\"公寓存放点\"," +
                "\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_APARTMENT_DEPOSIT\"}],\"optionTitle\":\"\"}}]}}";
    }

    /**
     * 创建外卖存放点-写字楼存放点-有人值守笔记请求体
     */
    private String createOfficeBuildingDepositMannedNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.22753960217084\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区666H+M33 邮政编码: 310008\"," +
                "\"noteStatus\":1,\"latitude\":\"30.211427363853367\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\"," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0,\"name\":\"写字楼存放点\"," +
                "\"optionResp\":{\"markOptionList\":[{\"genre\":0,\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_MANNED\"," +
                "\"name\":\"有人值守\"}],\"optionTitle\":\"\"},\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_OFFICE_BUILDING_DEPOSIT\"}]}," +
                "\"genre\":0,\"name\":\"外卖存放点\"}]}}";
    }

    /**
     * 创建外卖存放点-写字楼存放点-其他笔记请求体
     */
    private String createOfficeBuildingDepositOtherNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.22448664352038\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区阡陌路482号智慧e谷大楼B 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.20807855410564\"," +
                "\"items\":{\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\"," +
                "\"genre\":0,\"name\":\"外卖存放点\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"genre\":0,\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_OFFICE_BUILDING_DEPOSIT\"," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_OTHER\"," +
                "\"name\":\"其他\",\"genre\":0}]},\"name\":\"写字楼存放点\"}]}}],\"optionTitle\":\"\"}}";
    }

    /**
     * 创建外卖存放点-其他公共可存放点-有人值守笔记请求体
     */
    private String createOtherPublicDepositMannedNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.22152921750943\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区物联网街665C+9F7 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.208894676003954\"," +
                "\"items\":{\"markOptionList\":[{\"genre\":0,\"name\":\"外卖存放点\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_OTHER_PUBLIC_DEPOSIT\",\"genre\":0," +
                "\"optionResp\":{\"markOptionList\":[{\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_MANNED\"," +
                "\"name\":\"有人值守\",\"genre\":0}],\"optionTitle\":\"\"},\"name\":\"其他公共可存放点\"}]}}]," +
                "\"optionTitle\":\"\"}}";
    }

    /**
     * 创建外卖存放点-其他公共可存放点-无人值守笔记请求体
     */
    private String createOtherPublicDepositUnmannedNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.21687638785136\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区江陵路1519号 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.203553855529293\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\",\"name\":\"外卖存放点\"," +
                "\"optionResp\":{\"markOptionList\":[{\"genre\":0,\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"genre\":0,\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_UNMANNED\"," +
                "\"name\":\"无人值守\"}]},\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_OTHER_PUBLIC_DEPOSIT\"," +
                "\"name\":\"其他公共可存放点\"}],\"optionTitle\":\"\"}}]}}";
    }

    /**
     * 创建外卖存放点-其他公共可存放点-其他笔记请求体
     */
    private String createOtherPublicDepositOtherNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.2120861935408\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区春波南苑45幢 邮政编码: 310051\"," +
                "\"noteStatus\":1,\"latitude\":\"30.187331105899123\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"optionResp\":{\"markOptionList\":[{" +
                "\"key\":\"MAP_NOTE_COLLECTION_POINT_TYPE_OTHER_PUBLIC_DEPOSIT\",\"name\":\"其他公共可存放点\"," +
                "\"genre\":0,\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"key\":\"MAP_NOTE_COLLECTION_POINT_SUB_TYPE_OTHER\"," +
                "\"name\":\"其他\",\"genre\":0}]}}],\"optionTitle\":\"\"},\"key\":\"MAP_NOTE_LOCATION_TYPE_TAKEOUT_COLLECTION_POINT\"," +
                "\"name\":\"外卖存放点\",\"genre\":0}]}}";
    }

    /**
     * 创建路况-交通拥堵-不可通行笔记请求体
     */
    private String createTrafficCongestionNotAccessibleNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.2477407044617\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市萧山区飞虹路1125号 邮政编码: 310008\"," +
                "\"noteStatus\":1,\"latitude\":\"30.223833951292043\"," +
                "\"items\":{\"optionTitle\":\"\",\"markOptionList\":[{\"name\":\"路况\",\"genre\":0," +
                "\"optionResp\":{\"markOptionList\":[{\"genre\":0,\"name\":\"交通拥堵\"," +
                "\"optionResp\":{\"optionTitle\":\"\",\"markOptionList\":[{\"genre\":0,\"name\":\"不可通行\"," +
                "\"key\":\"MAP_NOTE_TRAFFIC_CONGESTION_TYPE_NOT_ACCESSIBLE\"}]}," +
                "\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONGESTION\"}],\"optionTitle\":\"\"}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\"}]}}";
    }

    /**
     * 创建路况-交通拥堵-缓慢通行笔记请求体
     */
    private String createTrafficCongestionSlowPassageNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.22265148081914\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区滨和路滨和路阡陌路口西 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.200633273211825\"," +
                "\"items\":{\"markOptionList\":[{\"name\":\"路况\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONGESTION\",\"name\":\"交通拥堵\"," +
                "\"genre\":0,\"optionResp\":{\"markOptionList\":[{\"name\":\"缓慢通行\"," +
                "\"key\":\"MAP_NOTE_TRAFFIC_CONGESTION_TYPE_SLOW_PASSAGE\",\"genre\":0}],\"optionTitle\":\"\"}}]}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\",\"genre\":0}],\"optionTitle\":\"\"}}";
    }

    /**
     * 创建路况-交通拥堵-单车道笔记请求体
     */
    private String createTrafficCongestionSingleLaneNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.23010377923573\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区七闸河村3-21号 邮政编码: 310008\"," +
                "\"noteStatus\":1,\"latitude\":\"30.212620904772017\"," +
                "\"items\":{\"markOptionList\":[{\"name\":\"路况\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"name\":\"交通拥堵\",\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONGESTION\"," +
                "\"genre\":0,\"optionResp\":{\"markOptionList\":[{\"genre\":0,\"name\":\"单车道\"," +
                "\"key\":\"MAP_NOTE_TRAFFIC_CONGESTION_TYPE_SINGLE_LANE_TRAFFIC\"}],\"optionTitle\":\"\"}}]}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\",\"genre\":0}],\"optionTitle\":\"\"}}";
    }

    /**
     * 创建路况-交通管制-工作日有管制笔记请求体
     */
    private String createTrafficControlWeekdayNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.21795878007033\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区江陵路6629+44R 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.200031977865507\"," +
                "\"items\":{\"markOptionList\":[{\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"name\":\"交通管制\",\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONTROL\"," +
                "\"genre\":0,\"optionResp\":{\"markOptionList\":[{\"name\":\"工作日有管制\",\"genre\":0," +
                "\"key\":\"MAP_NOTE_TRAFFIC_CONTROL_TYPE_WEEKDAY_CONTROL\"}],\"optionTitle\":\"\"}}]}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\",\"genre\":0,\"name\":\"路况\"}]}}";
    }

    /**
     * 创建路况-交通管制-周末有管制笔记请求体
     */
    private String createTrafficControlWeekendNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.21933722597845\"," +
                "\"anchorPointAddress\":\"中国杭州市滨江区迎春北苑 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.1986453373714\"," +
                "\"items\":{\"markOptionList\":[{\"optionResp\":{\"markOptionList\":[{\"name\":\"交通管制\"," +
                "\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONTROL\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"name\":\"周末有管制\",\"key\":\"MAP_NOTE_TRAFFIC_CONTROL_TYPE_WEEKEND_CONTROL\"," +
                "\"genre\":0}]},\"genre\":0}],\"optionTitle\":\"\"},\"genre\":0,\"name\":\"路况\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\"}]}}";
    }

    /**
     * 创建路况-交通管制-其他笔记请求体
     */
    private String createTrafficControlOtherNoteBody() {
        return "{\"areaId\":58,\"images\":\"\",\"longitude\":\"120.2267510520976\"," +
                "\"anchorPointAddress\":\"中国浙江省杭州市滨江区滨成科创公寓7号 邮政编码: 311200\"," +
                "\"noteStatus\":1,\"latitude\":\"30.19663964478008\"," +
                "\"items\":{\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\"," +
                "\"optionResp\":{\"markOptionList\":[{\"key\":\"MAP_NOTE_TRAFFIC_TYPE_TRAFFIC_CONTROL\"," +
                "\"genre\":0,\"name\":\"交通管制\",\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"genre\":0,\"key\":\"MAP_NOTE_TRAFFIC_CONTROL_TYPE_OTHER\"," +
                "\"name\":\"其他\"}]}}],\"optionTitle\":\"\"},\"name\":\"路况\",\"genre\":0}]}}";
    }

    /**
     * 创建路况-其他交通路况问题笔记请求体
     */
    private String createOtherTrafficIssuesNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"longitude\":\"120.23855491044583\"," +
                "\"anchorPointAddress\":\"668Q+XC 中国浙江省杭州市萧山区\",\"noteStatus\":1," +
                "\"latitude\":\"30.21746987813603\"," +
                "\"items\":{\"markOptionList\":[{\"key\":\"MAP_NOTE_LOCATION_TYPE_TRAFFIC\"," +
                "\"name\":\"路况\",\"genre\":0,\"optionResp\":{\"optionTitle\":\"\"," +
                "\"markOptionList\":[{\"key\":\"MAP_NOTE_TRAFFIC_TYPE_OTHER_TRAFFIC_ISSUES\"," +
                "\"name\":\"其他交通路况问题\",\"genre\":0}]}}]}}";
    }

    /**
     * 创建楼号笔记请求体
     */
    private String createBuildingNumberNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"latitude\":\"30.203320700000013\"," +
                "\"anchorPointAddress\":\"中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司\"," +
                "\"items\":{\"markOptionList\":[{\"code\":1,\"genre\":0,\"name\":\"楼号\"," +
                "\"optionResp\":{\"markOptionList\":[{\"code\":0,\"genre\":1,\"name\":\"自动化-楼号笔记\"," +
                "\"optionResp\":{\"markOptionList\":[{\"code\":0,\"genre\":0,\"name\":\"步行上楼\"," +
                "\"key\":\"MAP_NOTE_BUILDING_SUB_TYPE_WALK_UP\"}]},\"key\":\"MAP_NOTE_BUILDING_TYPE_TEXT\"}]}," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_BUILDING_NUMBER\"}]},\"longitude\":\"120.2168115\",\"noteStatus\":1}";
    }

    /**
     * 创建商家笔记请求体
     */
    private String createShopNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"latitude\":\"30.203318399999986\"," +
                "\"anchorPointAddress\":\"中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司\"," +
                "\"items\":{\"markOptionList\":[{\"code\":2,\"genre\":0,\"name\":\"商家\"," +
                "\"optionResp\":{\"markOptionList\":[{\"code\":0,\"genre\":1,\"name\":\"自动化-商家笔记\"," +
                "\"key\":\"MAP_NOTE_SHOP_TYPE_TEXT\"}]},\"key\":\"MAP_NOTE_LOCATION_TYPE_SHOP\"}]}," +
                "\"longitude\":\"120.2168211\",\"noteStatus\":1}";
    }

    /**
     * 创建自定义笔记请求体
     */
    private String createCustomNoteBody() {
        return "{\"images\":\"\",\"areaId\":58,\"latitude\":\"30.203317600000002\"," +
                "\"anchorPointAddress\":\"中华人民共和国浙江省杭州市中国浙江省杭州市滨江区江陵路1740号310051杭州骏宝行汽车销售服务有限公司\"," +
                "\"items\":{\"markOptionList\":[{\"code\":5,\"genre\":1,\"name\":\"自动化-自定义笔记\"," +
                "\"key\":\"MAP_NOTE_LOCATION_TYPE_TEXT\"}]},\"longitude\":\"120.21681820000003\",\"noteStatus\":1}";
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("operatingsystem", "2");
        headers.put("user-agent", "PandaDelivery/5.55.0 (iPhone; iOS 16.7.2; Scale/3.00) OKPOS");
        headers.put("brand", "iPhone X");
        headers.put("latitude", "30.203535");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.55.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "BFEC8953-4F04-4AAC-9C06-7EB2C8CA1411");
        headers.put("longitude", "120.216870");
        headers.put("accept-language", "zh-Hans-CN;q=1, en-CN;q=0.9, ja-CN;q=0.8, en-AU;q=0.7");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept", "*/*");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }
}
