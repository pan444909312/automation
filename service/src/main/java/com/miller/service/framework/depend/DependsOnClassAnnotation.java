package com.miller.service.framework.depend;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * 处理测试类之间的执行顺序，通过使用 {@link DependsOnClass @DependsOnClass}注解指定类之前的依赖关系。
 * 需要在resources目录下添加 junit-platform.properties 文件指定使用定义的类执行顺序，配置如下:
 * <pre>
 * junit.jupiter.testclass.order.default=com.xxx.framework.depend.DependsOnClassAnnotation
 * </pre>
 *
 * @author Miller Shan
 * @version 1.0
 * @see DependsOnClass
 * @see ClassOrderer
 * @since 2023/10/17 15:22:59
 */
public class DependsOnClassAnnotation implements ClassOrderer {
    private static final Logger logger = LoggerFactory.getLogger(DependsOnClassAnnotation.class);
    // 首先执行独立的测试类
    private static final int INDEPENDENT_TEST_CLASS_PRIORITY = 0;

    @Override
    public void orderClasses(ClassOrdererContext context) {
        // 获取待执行的类的所有依赖映射关系
        Map<String, Class[]> dependencyMap = context.getClassDescriptors().stream()
                .filter(descriptor -> descriptor.isAnnotated(DependsOnClass.class))
                .collect(toMap(descriptor -> descriptor.getTestClass().getName(),
                        descriptor -> descriptor.findAnnotation(DependsOnClass.class).map(DependsOnClass::value).get()));

        Map<String, Integer> dependencySize = new HashMap<>();

        try {
            // 遍历依赖项
            for (String name : dependencyMap.keySet()) {
                countAnnotationClassOrder(name, dependencyMap, dependencySize);
            }
        } catch (IllegalArgumentException exception) {
            logger.error(exception, () -> "ERROR - @DependsOnClass has cyclic dependencies!");
        }
        context.getClassDescriptors().sort(
                Comparator.comparing(
                        descriptor -> dependencySize.getOrDefault(descriptor.getTestClass().getName(), INDEPENDENT_TEST_CLASS_PRIORITY)));
    }

    // 计算依赖项之前的数量
    private final int countAnnotationClassOrder(String name, Map<String, Class[]> dependencyMap, Map<String, Integer> dependencySize) {
        // 递归出口
        if (dependencySize.containsKey(name)) {
            return dependencySize.get(name);
        }
        dependencySize.put(name, -1);
        // 获取类获取类上依赖的类列表
        Class[] ancestors = dependencyMap.get(name);
        int total = 1;

        if (ancestors != null) {
            for (Class ancestor : ancestors) {
                int sz = countAnnotationClassOrder(ancestor.getName(), dependencyMap, dependencySize);
                if (sz == -1) {
                    throw new IllegalArgumentException(String.format("Cyclic dependencies %s and %s", name, ancestor));
                }
                total += sz;
            }
        }

        dependencySize.put(name, total);
        return total;
    }

}
