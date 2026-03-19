```bash
# 参数：用例名称 和 cURL 命令（作为一个整体参数）
TESTCASE_NAME="$1"
CURL_COMMAND="$2"

# 检查参数
if [ -z "$TESTCASE_NAME" ] || [ -z "$CURL_COMMAND" ]; then
    echo "错误：缺少参数" >&2
    echo "用法：$0 <测试用例名称> <cURL命令>" >&2
    exit 1
fi

# 进入项目根目录
cd /Users/mossi/IdeaProjects/autotest-platform_Java || exit 1

# 编译
#如果未修改TestcaseFactory且未修改TestcaseFactoryWithData，则不执行编译
mvn clean package -pl testcase-pandafresh -am

# 构建 classpath
CP="testcase-delivery/target/classes:service/target/classes:$(find ~/.m2/repository -name '*.jar' -type f | tr '\n' ':')"

# 执行 Java 程序，传递参数
java -cp "$CP" com.miller.delivery.testcase.factory.TestcaseFactoryWithData "$TESTCASE_NAME" "$CURL_COMMAND"

# 获取 Java 程序的退出码并返回
exit $?
```