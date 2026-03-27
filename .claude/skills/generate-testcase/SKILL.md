---

[//]: # (## name：generate-testcase)

[//]: # (description: 根据 curl 和接口响应生成自动化用例)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 触发方式)

[//]: # (当用户使用 `/生成自动化用例` 命令时触发，用户需要提供：)

[//]: # (1. 接口的 curl 命令)

[//]: # (2. 接口的响应结果 JSON)

[//]: # (3. （可选）用例名称、模块路径、是否需要登录、额外断言说明)

## 使用示例
```
/生成自动化用例
curl 'https://app-test.hungrypanda.cn/api/user/member/buyMemberDetail' -H 'authorization: xxx' ...
响应：{"resultCode":1000,"result":{...}}
用例名称：会员购买页详情
模块路径：account/member/buymemberdetail
需要登录：是
```

## 执行步骤

### 第一步：解析 curl 命令
从用户提供的 curl 命令中提取以下信息：
- **请求 URL**：提取域名后的 path 部分（不含域名，域名统一使用 `TestcaseConfig.HOST_APP`）
- **请求方法**：GET / POST / PUT / DELETE / PATCH
- **请求头 Headers**：所有 `-H` 参数
- **请求体 Body**：`--data-raw` 或 `-d` 或 `--data` 的内容（POST/PUT/PATCH 请求）
- **URL 参数 Params**：URL 中 `?` 后面的查询参数（GET 请求常见）

### 第二步：解析接口响应
从用户提供的响应 JSON 中：
- 解析完整的 JSON 结构
- 生成断言文件内容：将动态变化的字段值替换为 json-unit 占位符：
  - 时间戳类字段（如 `nowTime`、`time`、`createTime`、`updateTime`）→ `${json-unit.any-number}` 或 `${json-unit.ignore-element}`
  - ID 类字段（如 `xxxId`、`id`）→ `${json-unit.any-number}`
  - Token/签名类字段 → `${json-unit.any-string}`
  - URL 类字段（含 http/https 的值）→ `${json-unit.any-string}`
  - 其他明显动态字段 → 根据类型使用 `${json-unit.any-number}` 或 `${json-unit.any-string}`
  - 保留业务含义明确的固定值（如 `resultCode: 1000`、`success: true`、枚举值等）

### 第三步：确定文件路径
根据用户提供的模块路径（或从 URL path 推导），确定以下路径：

- **Java 类路径**：`testcase-user-app/src/main/java/com/miller/testcase/module/{模块路径}/{类名}.java`
- **资源文件路径**：`testcase-user-app/src/main/resources/module/{模块路径}/`
  - `response/assert_full_field.json` — 断言文件

如果用户没有提供模块路径，则从 URL path 推导。例如：
- `/api/user/member/buyMemberDetail` → `account/member/buymemberdetail`
- `/api/user/v1/shop/info` → `home/shop/getshopinfo`
- `/api/app/user/v1/address/edit` → `account/address/createaddress`

推导规则：根据 URL 中的业务关键词映射到已有的模块目录结构。如果无法确定，询问用户。

### 第四步：生成类名
- 如果用户提供了用例名称，将其转为 PascalCase 作为类名（去掉空格和特殊字符，每个单词首字母大写）
- 如果是中文名称，使用拼音或用户指定的英文名
- 类名不需要 `_Tests` 后缀（参考 demo：`BuymemberdetailHasAllianceCoupon`）

### 第五步：生成 Java 测试类文件

使用以下模板生成测试类，根据实际情况调整：

```java
package com.miller.testcase.module.{包路径用点分隔};

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * {用例描述}
 *
 * @author {从 git config user.name 获取}
 * @version 2.0
 * @since {当前日期时间 yyyy/MM/dd HH:mm:ss}
 */
@Scenario(
        scenarioID = "{生成一个 ULID}", // 自动生成，不要修改
        scenarioName = "{用例名称}",
        author = "{从 git config user.email 获取}", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("{用例名称}")
public class {类名} {
    String uri = TestcaseConfig.HOST_APP + "{接口path}";
    String method = "{GET/POST/PUT/DELETE}";
    String params = {如果有URL参数则为资源文件路径字符串，否则为 null};
    String assertFullField = "module/{模块路径}/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 如果需要登录，添加以下行（根据用户说明决定）：
        // headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        // 如果 curl 中有特殊 header 需要覆盖（如 latitude/longitude），在这里 put

        // 步骤2: 设置请求参数/请求体
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, headers, {requestBody});

        // 步骤4: 断言
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
```

**关键规则**：
- **GET 请求无 body**：`sendRequest` 的最后一个参数传 `null`
- **POST/PUT/DELETE 有 body**：需要额外声明 `String body = "module/{路径}/request/body.json";`，并通过 `TestCaseHelpful.getJsonRequestBody(body)` 获取，传给 `sendRequest`
- **有 URL 参数**：声明 `String params = "module/{路径}/request/params.json";`
- **需要登录**：在 headers 中 put Authorization
- **headers 默认使用** `module/headers.json`，除非 curl 中有明显不同的 header 需要单独处理
- **curl 中的特殊 header**（如自定义的 latitude、longitude 等非标准 header）需要在代码中通过 `headers.put()` 设置

### 第六步：生成资源文件

1. **断言文件** `response/assert_full_field.json`：
   - 基于用户提供的响应 JSON 生成
   - 动态字段替换为 json-unit 占位符（见第二步规则）
   - 格式化为 pretty JSON

2. **请求体文件** `request/body.json`（仅 POST/PUT/DELETE）：
   - 从 curl 的 `--data-raw` / `-d` 提取
   - 格式化为 pretty JSON
   - 保留 null 字段

3. **请求参数文件** `request/params.json`（仅当有 URL 查询参数时）：
   - 从 URL 的 `?key=value&key2=value2` 提取
   - 转为 JSON 对象格式

### 第七步：生成 ULID
使用 bash 命令生成一个 ULID 格式的 scenarioID：
```bash
python3 -c "
import time, random, string
ENCODING = '0123456789ABCDEFGHJKMNPQRSTVWXYZ'
t = int(time.time() * 1000)
ulid = ''
for i in range(10):
    ulid = ENCODING[t & 0x1F] + ulid
    t >>= 5
for i in range(16):
    ulid += ENCODING[random.randint(0, 31)]
print(ulid)
"
```

### 第八步：获取 Git 信息
```bash
git config user.name
git config user.email
```

## 输出要求
1. 创建所有必要的目录
2. 写入 Java 测试类文件
3. 写入断言 JSON 文件
4. 如有请求体，写入 body.json
5. 如有 URL 参数，写入 params.json
6. 输出生成的文件列表，方便用户确认

## 注意事项
- 域名统一使用 `TestcaseConfig.HOST_APP`，不要硬编码域名
- headers 优先使用公共的 `module/headers.json`，只在代码中 put 差异化的 header
- 断言使用 `Option.IGNORING_EXTRA_FIELDS` 模式，容忍响应中多出的字段
- scenarioID 必须是唯一的 ULID
- 包路径和资源路径要保持一致对应
- 如果用户没有明确说明是否需要登录，检查 curl 中的 authorization header：如果有值则需要登录
- 生成的代码风格要与项目中已有用例保持一致
