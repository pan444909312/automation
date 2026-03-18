---
## name：edit-response-origin
description: 根据json响应，修改响应期望值

---

## 执行步骤

### 1、需用户额外提供所需需求用例名称
- 如：Testskill01_Tests

### 2、找到用例中assertFullField的文件路径

### 3、需用户提供响应结果json内容

### 4、解析响应结果json
从用户提供的响应 JSON 中：

解析完整的 JSON 结构
- 生成断言文件内容：将动态变化的字段值替换为 json-unit 占位符：
- 时间戳类字段（如 nowTime、time、createTime、updateTime）→ ${json-unit.any-number} 或 ${json-unit.ignore-element}
- ID 类字段（如 xxxId、id）→ ${json-unit.any-number}
- Token/签名类字段 → ${json-unit.any-string}
- URL 类字段（含 http/https 的值）→ ${json-unit.any-string}
- 其他明显动态字段 → 根据类型使用 ${json-unit.any-number} 或 ${json-unit.any-string}或${json-unit.ignore-element}
- 保留业务含义明确的固定值（如 resultCode: 1000、success: true、枚举值等）

### 5、将整理好的响应内容修改到对应assert_full_field.json文件中