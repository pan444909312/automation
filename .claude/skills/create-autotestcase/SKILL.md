---
## name：create-autotestcase
description: 根据 curl 和接口响应生成自动化用例

---

## 执行步骤

### 第一步：收集用户输入
询问用户提供以下信息：
- 测试用例名称:TESTCASE_NAME=测试用例名称
- 功能所属模块，如（C端、D端、PF）：C端、D端、PF
- 提供功能文件路径：如home/channel
- 是否需要登录？需要登录则需要用户提供登录用户手机号以及登录密码
- 从 Charles 拷贝的 cURL 命令：  CURL_COMMAND=cURL 命令

### 第二步：修改用例生成方法
#### 1、根据用户选择的模块，指定对应模块：
其中 `$MODULE_NAME` 根据模块确定：
- C端：`testcase-user-app`
- D端：`testcase-delivery`
- PF：`testcase-pandafresh`

#### 2、修改对应模块的TestcaseFactoryWithData、TestcaseFactory中的文件路径
- 示例：public static String CUSTOM_PATH = "home/channel";
- 示例：public static String CUSTOM_SUB_PATH = "home/channel";


### 第三步：执行用例生成方法
根据用户在第一步选择的模块，调用对应的 TestcaseFactoryWithData.main() 方法：

#### 1. 执行 TestcaseFactoryWithData.main() 方法
使用 Java 命令执行对应模块的 TestcaseFactoryWithData.main() 方法：
- C端（testcase-user-app）：执行[c端用例生成](/reference/c_test_factory.md)
- D端（testcase-delivery）：执行[d端用例生成](/reference/d_test_factory.md)
- PF端（testcase-pandafresh）：执行[pf用例生成](/reference/pf_test_factory.md)

#### 2. 登录处理
需要登录： 在用例的请求头后添加代码：requestHeaders.put("authorization",TestCaseHelpful.login(登录手机号,登录密码));

#### 3. 验证生成结果
执行成功后，程序会：
- 输出 "测试用例生成成功！"


### 第四步：修改响应结果
询问用户是否需要修改响应结果参数
- 需要修改，则使用edit response.md中的规则修改响应
- 无需修改，则结束任务

