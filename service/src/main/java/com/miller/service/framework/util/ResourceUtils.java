package com.miller.service.framework.util;

import com.miller.service.framework.clz.ClassFindService;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * Resource 文件工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/4/30 16:09:10
 */
@Slf4j
public class ResourceUtils {
    private static String DEFAULT_TESTCASE_DATA_FILE_PATH = "testdata";

    public ResourceUtils() {
        /*
         * 默认测试用例数据文件路径
         */
        try {
            Optional<String> defaultTestCaseDataFilePath = Optional.ofNullable(new PropertiesUtils().getProperty(this.getClass(), "testcase.data.file.path"));
            DEFAULT_TESTCASE_DATA_FILE_PATH = defaultTestCaseDataFilePath.orElse("testdata");
        } catch (RuntimeException runtimeException) {
            DEFAULT_TESTCASE_DATA_FILE_PATH = "testdata";
        }
    }


    /**
     * 读取 resources 目录下的 测试用例数据 目录下的文件内容并将其作为字符串返回
     *
     * @param clazz    当前类必须要传，否则在多模块 SpringBoot 环境下获取的永远都是运行jar的资源文件中的属性值
     * @param fileName resources目录下的文件名.例如：application.properties
     * @return 测试用例数据
     */
    public String readTestCaseDataFromResourcesPath(Class<?> clazz, String fileName) {
        // 获取资源的输入流
        // InputStream inputStream = ResourceReader.class.getResourceAsStream(File.separator + DEFAULT_TESTCASE_DATA_FILE_PATH);
        // 使用 ClassFindService 根据类获取类所在的配置文件
        URL inputStream = ClassFindService.getResourcePathByClz(clazz, DEFAULT_TESTCASE_DATA_FILE_PATH);
        if (inputStream == null) {
            throw new IllegalArgumentException("目录不存在, 请在 resources 目录下创建 testdata 目录");
        }

        // 首先加载默认的配置文件
        Properties defalutProperties = new PropertiesUtils().loadConfig(clazz, "application.properties");

        String envProperty = defalutProperties.getProperty("spring.profiles.active");
        if (null != envProperty && !envProperty.isEmpty()) {
            // 区分环境，读取配置文件..例如：/ testdata / test / 文件名称
            return readFileFromResourcesPath(clazz, DEFAULT_TESTCASE_DATA_FILE_PATH + File.separator + envProperty + File.separator + fileName);
        } else {
            // 不区分环境，读取配置文件.例如：/ testdata / 文件名称
            return readFileFromResourcesPath(clazz, DEFAULT_TESTCASE_DATA_FILE_PATH + File.separator + fileName);

        }
    }


    /**
     * 读取 resources 目录下的文件内容并将其作为字符串返回
     *
     * @param clazz    当前类必须要传，否则在多模块 SpringBoot 环境下获取的永远都是运行jar的资源文件中的属性值
     * @param fileName resources目录下的文件名.例如：application.properties
     * @return 文件内容
     */
    public String readFileFromResourcesPath(Class<?> clazz, String fileName) {
        // 如果文件名不以/开头，则添加一个/ 路径分隔符
        // if (!fileName.startsWith("/")) fileName = File.separator + fileName;
        // 获取资源的输入流
//        InputStream inputStream = ResourceReader.class.getResourceAsStream(fileName);
        // 使用 ClassFindService 根据类获取类所在的配置文件
        URL inputStream = ClassFindService.getResourcePathByClz(clazz, fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        }
        // 使用BufferedReader读取内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream.openStream(), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            log.error("读取文件内容失败", e);
            throw new RuntimeException(e);
        }
    }
}
