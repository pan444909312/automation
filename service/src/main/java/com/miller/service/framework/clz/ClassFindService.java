package com.miller.service.framework.clz;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.core.io.support.ResourcePatternUtils.getResourcePatternResolver;

@Slf4j
@Component
public class ClassFindService {

    @Resource
    private ConfigurableApplicationContext applicationContext;

    private final Map<String, Class<?>> clzNameList = new HashMap<>(100);

    public static final ClassLoader clzLoader = ClassFindService.class.getClassLoader();

    @PostConstruct
    public void scanAllClass() throws IOException {
        String basePackage = "com.miller";
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(applicationContext.getEnvironment()
                        .resolveRequiredPlaceholders(basePackage))
                + '/' + "**/*.class";
        // 根据路径转换为Resource,本质是一个输入流
        org.springframework.core.io.Resource[] resources = getResourcePatternResolver(applicationContext).getResources(packageSearchPath);

        String packageDir = "/" + basePackage.replace('.', '/') + "/";

        for (int i = 0; i < resources.length; i++) {
            String urlPath = resources[i].getURL().getPath();
            int idx = urlPath.indexOf(packageDir);
            if (idx < 0 || !urlPath.endsWith(".class")) {
                continue;
            }

            String className = urlPath.substring(idx + 1, urlPath.length() - 6).replace('/', '.');
            try {
                clzNameList.put(className, clzLoader.loadClass(className));
            } catch (Exception e) {
                log.error("class loader error:{}", className);
            }
        }
    }

    public List<Class<?>> getPackageClass(String packagePath) {
        return clzNameList.entrySet().stream()
                .filter(v -> v.getKey().startsWith(packagePath))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
