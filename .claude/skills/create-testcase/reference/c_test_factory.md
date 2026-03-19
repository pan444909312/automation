```bash
#!/bin/bash
set -euo pipefail

if [ $# -ne 2 ]; then
    echo "错误：需要两个参数" >&2
    echo "用法：$0 <测试用例名称> <cURL命令>" >&2
    exit 1
fi

TESTCASE_NAME="$1"
CURL_COMMAND="$2"

# 进入项目根目录（假设脚本在 bin/ 下）
SCRIPT_DIR="$(cd "$(autotest-platform_Java "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"  
cd "$PROJECT_ROOT"

# 编译
echo "开始 Maven 编译..."
if ! mvn package -pl testcase-user-app -am; then
    echo "Maven 编译失败" >&2
    exit 1
fi

# 获取精确 classpath
echo "构建 classpath..."
CP=$(mvn -pl testcase-user-app dependency:build-classpath -DincludeScope=compile -Dmdep.outputFile=/dev/stdout | tail -1)
CP="testcase-user-app/target/classes:service/target/classes:$CP"

# 可选：对 cURL 命令进行 base64 编码
CURL_B64=$(echo -n "$CURL_COMMAND" | base64)

# 执行 Java 程序
echo "执行 Java 程序..."
java -cp "$CP" com.miller.testcase.factory.TestcaseFactoryWithData "$TESTCASE_NAME" "$CURL_B64"

exit $?
```