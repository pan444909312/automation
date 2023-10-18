package com.miller.service.framework.depend;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通过{@link DependsOnMethod @DependsOnMethod}注解实现方法的依赖执行顺序管理。
 *
 * @author Miller Shan
 * @version 1.0
 * @see DependsOnMethod
 * @see MethodOrderer
 * @since 2023/10/17 16:24:58
 */
public class DependsOnMethodAnnotation implements MethodOrderer {
    private static final Logger logger = LoggerFactory.getLogger(DependsOnMethodAnnotation.class);
    /**
     * INDEPENDENT_TEST_PRIORITY: 独立的{@code @Test}测试方法优先级为0，最先被执行。然后在执行有依赖的测试方法
     */
    private static final int INDEPENDENT_TEST_PRIORITY = 0;

    @Override
    public void orderMethods(MethodOrdererContext context) {
        // 通过 MethodDescriptor 获取方法信息，比如：名称、注解等
        Map<String, String[]> dependencyMap = context.getMethodDescriptors().stream()
                // 从所有获得方法中过滤出含有DependsOnMethod注解的方法
                .filter(descriptor -> descriptor.isAnnotated(DependsOnMethod.class))
                // 使用收集器将流中过滤的结果整合
                .collect(Collectors.toMap(
                        descriptor -> descriptor.getMethod().getName(),
                        descriptor -> descriptor.findAnnotation(DependsOnMethod.class).map(DependsOnMethod::value).get()));

        Map<String, Integer> dependencySize = new HashMap<>();

        try {
            // 遍历依赖项，计算执行的优先级
            for (String name : dependencyMap.keySet()) {
                countAnnotationMethodOrder(name, dependencyMap, dependencySize);
            }
        } catch (IllegalArgumentException exception) {
            logger.error(exception, () -> "ERROR - @DependsOnMethod has cyclic dependencies!!");
        }
        // 将方法按照优先级排序
        context.getMethodDescriptors().sort(
                Comparator.comparing(descriptor -> dependencySize.getOrDefault(descriptor.getMethod().getName(), INDEPENDENT_TEST_PRIORITY)));
    }

    // 计算依赖项之前的数量
    private final int countAnnotationMethodOrder(String name, Map<String, String[]> dependencyMap, Map<String, Integer> dependencySize) {
        // 递归出口
        if (dependencySize.containsKey(name)) {
            return dependencySize.get(name);
        }
        dependencySize.put(name, -1);
        // 通过方法名称获取方法注解上的值
        String[] ancestors = dependencyMap.get(name);
        int total = 1;

        if (ancestors != null) {
            // 遍历注解上的值,找到方法依赖的方法名
            for (String ancestor : ancestors) {
                int sz = countAnnotationMethodOrder(ancestor, dependencyMap, dependencySize);
                if (sz == -1) {
                    throw new IllegalArgumentException(String.format("Cycle detected between %s and %s", name, ancestor));
                }
                total += sz;
            }
        }
        // 存储方法名称和优先级
        dependencySize.put(name, total);
        return total;
    }
}
