package com.miller.userapp.module.data.user;

import com.alibaba.fastjson.JSON;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.util.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 一键自动创建用户
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/10/28 21:48:29
 */
@Disabled
@Scenario(scenarioID = "01JB9P4468E7XZD6EJ8J5K0D94",
        scenarioName = "一键自动创建模板用户", author = "luwei@hungrypandagroup.com", developmentTime = 6 * 60, maintenanceTime = 0, manualTestTime = 2 * 60)
@Slf4j
@DisplayName("一键自动创建用户")
public class CreateUserTests {
    @Test
    @DisplayName("工具-一键自动创建用户")
    public void createUserTest() {
        String createUserJson = """
                {
                    "loginPassword":"12345678",
                    "payPassword":"123456",
                    "balance":1000000,
                    "address":{
                        "addressRemark": "",
                	    "postcode": "310000",
                	    "longitude": "120.22185",
                	    "buildingName": "星耀中心",
                	    "countryCode": "86",
                	    "isDefault": "0",
                	    "addTag": 2,
                	    "address": "China, Zhejiang, Hangzhou, Binjiang District, 072, 东北方向160米星耀中心",
                	    "houseNum": "1288",
                	    "latitude": "30.20074",
                	    "contacts": "手机号",
                	    "type": 1,
                	    "telephone": "19240377998",
                	    "gender": 1
                    }
                    
                }
                """;
        CreateUserEntity createUserData = JSON.parseObject(createUserJson, CreateUserEntity.class);
        CreateUserServer createUserServer = new CreateUserServer(DBUtils.getDBOfPandaTest(), createUserData);
        String result = createUserServer.autoCreateUser(true, null);
        System.out.println("result is : " + result);
//        createUserServer.autoCreateUser(false,"19225568102");
    }
}
