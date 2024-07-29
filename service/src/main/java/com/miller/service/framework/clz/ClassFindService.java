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
import java.util.*;
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
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage)) + '/' + "**/*.class";
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

    /**
     * 获取指定包下的类集合
     *
     * @param packagePath 包路径
     * @return 包下的类集合
     */
    public static List<Class<?>> getPackageClass(String packagePath) {
        return clzNameMap.entrySet().stream().filter(v -> v.getKey().startsWith(packagePath)).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * 获取指定类的资源路径
     *
     * @param clazz    类
     * @param fileName 文件名称
     * @return 资源的流
     */
    public static URL getResourcePathByClz(Class<?> clazz, String fileName) {
        String clzPath = null;
        // 如果为空则不是 Spring 环境，则获取本地资源路径
        if (clzPathMap.isEmpty()) {
            // return clzLoader.getResourceAsStream(fileName);
            String fullPath = Objects.requireNonNull(clazz.getClassLoader().getResource(clazz.getName().replace(".", "/") + ".class")).getPath();
            int endIndex = fullPath.indexOf("classes");
            if (endIndex != -1) {
                String desiredPath = fullPath.substring(0, endIndex);
                clzPath = desiredPath + "classes";
            }
        } else {
            // Spring 环境启动时实例化类之后会执行 @PostConstruct  所以 clzPathMap 不为空，则直接获取
            clzPath = clzPathMap.get(clazz);
        }
        if (null == clzPath) {
            log.error("资源获取失败, class path 获得失败, class:{}", clazz);
            return null;
        }

        try {
            Iterator<URL> iterator = clzLoader.getResources(fileName).asIterator();
            while (iterator.hasNext()) {
                URL url = iterator.next();
                String filePath = url.getPath();
                if (filePath.startsWith(clzPath)) {
                    log.info("getResourcePathByClz:{}", filePath);
                    return url;
                }
            }
        } catch (Exception e) {
            log.error("资源获取失败: class:{} path:{}", clazz, fileName);
        }
        return null;
    }
}
