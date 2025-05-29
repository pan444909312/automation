package com.miller.testcasedelivery.业务模块.首页.登录;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcasedelivery.工具.测试用例助手;
import com.miller.testcasedelivery.配置.测试用例配置;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/26 23:19:42
 */

@Scenario(
        scenarioID = "01JW68KNTBJSEZ0GPXQ9AF6XFN",
        scenarioName = "用户-登录",
        author = "shandongdong@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 5, manualTestTime = 1)
@Disabled   // 这个仅仅是一个演示示例
public class 骑手登录测试场景 {
    // 接口请求的 path
    String 请求路径 = 测试用例配置.接口域名 + "/api/user/combine/login";
    // 请求方式
    String 请求方法 = "POST";
    // 请求头
    String 请求头数据 = "业务模块/公共请求头.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String 请求体内容 = "业务模块/首页/登录/请求数据/请求成功的请求数据.json";
    // 断言
    String 期望结果内容 = "业务模块/首页/登录/响应数据/校验响应结果的全部字段.json";

    @Test
    void 正常流程_登录() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var 请求头 = 测试用例助手.获取请求头(请求头数据);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var 请求体 = 测试用例助手.获取请求体(请求体内容);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var 接口返回结果 = 测试用例助手.发送HTTP请求(请求方法, 请求路径, null, 请求头, 请求体);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        var 期望结果 = 测试用例助手.获取文件内容(期望结果内容);
        // 方式一：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        测试用例助手.断言Json内容(接口返回结果).isEqualTo(期望结果);
    }
}
