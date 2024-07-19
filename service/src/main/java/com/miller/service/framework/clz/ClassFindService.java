package com.miller.service.framework.clz;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.core.io.support.ResourcePatternUtils.getResourcePatternResolver;

@Slf4j
@Component
public class ClassFindService {

    @Resource
    private ConfigurableApplicationContext applicationContext;

    // 通过类名称获得类，获得包下类使用
    private static final Map<String, Class<?>> clzNameMap = new HashMap<>(100);
    // 类所在路径Map, 获得类所在jar 资源使用
    private static final Map<Class<?>, String> clzPathMap = new HashMap<>(100);

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
                Class<?> clz = clzLoader.loadClass(className);
                clzNameMap.put(className, clz);
                clzPathMap.put(clz, urlPath.substring(0, idx));
            } catch (Exception e) {
                log.error("class loader error:{}", className);
            }
        }
    }

    public static List<Class<?>> getPackageClass(String packagePath) {
        return clzNameMap.entrySet().stream()
                .filter(v -> v.getKey().startsWith(packagePath))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static InputStream getResourcePathByClz(Class<?> clz, String file) {
        if (clzPathMap.isEmpty()) {
            return clzLoader.getResourceAsStream(file);
        }

        String clzPath = clzPathMap.get(clz);
        if (null == clzPath) {
            log.error("资源获取失败, class path 获得失败, class:{}", clz);
            return null;
        }

        try {
            Iterator<URL> iterator = clzLoader.getResources(file).asIterator();
            while (iterator.hasNext()) {
                URL url = iterator.next();
                String filePath = url.getPath();
                if (filePath.startsWith(clzPath)) {
                    log.info("getResourcePathByClz:{}", filePath);
                    return url.openStream();
                }
            }
        } catch (Exception e) {
            log.error("资源获取失败: class:{} path:{}", clz, file);
        }
        return null;
    }
}
