---
## name: copy-case
description: 从用户输入的接口用例名称，复制对应用例到指定目录，且修改用例名称

---

# Copy Case Skill

这个skill帮助用户复用已有用例，生成指定目录下的用例，且支持修改用例名称。

## 工作流程

当用户请求复制测试用例时，按以下步骤执行：

### 1. 根据用例名称找到需被复制的用例

当用户提供被复制的用例名称时，你应当：
- 首先查找到对应用例，并询问用户是否是对应用例
- 用户认为不是指定用例，则需用户提供更多信息进行定位
- 用户认为是指定用例时，则继续下一步操作


### 2. 确定需复制到的文件路径

根据用户提供的文件路径，确定以下路径是否已存在：
- 如果路径已存在，则不创建文件路径
- 如果路径不存在，则创建对应文件路径
- 如果用户没有提供模块路径，则需询问用户。



### 3. 用例输出

将被复制的文件，修改文件具体内容：
- 使用以下模板修改测试类，根据实际情况调整
- 1、用例名称、类名需询问用户提供
- 2、ULID通过使用 ULIDUtils方法生成
```  bash
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
- 3、author通过git config中获取
``` bash
git config user.name
git config user.email
```

```java

/**
 * {用例描述}
 *
 * @author {从 git config user.name 获取}
 * @version 2.0
 * @since 
 */
@Scenario(
        scenarioID = "{生成一个 ULID}", // 自动生成，不要修改
        scenarioName = "{用例名称}",
        author = "{从 git config user.email 获取}", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("{用例名称}")
public class {类名} {

    @DisplayName("正向流程")
}
```




### 4. 复制到对应路径完成

将修改好的用例，复制到对应路径下，告知用户用例生成。

### 5. 确认完成

告知用户用例生成。

