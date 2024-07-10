package com.miller.util;

import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * TODO 描述
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/7/10 15:46:43
 */
public class DynamicLoad {

    /**
     * 动态加载指定路径下指定jar包
     * @param path
     * @param fileName
     */
    public void loadJar(String path, String fileName) throws IOException {

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(("com.miller") + '/' + "**/*.class"));
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        for (Resource resource : resources) {
            try {
                URL url = resource.getURL();
                URLConnection urlConnection = url.openConnection();
                JarURLConnection jarURLConnection = (JarURLConnection)urlConnection;
                // 获取jar文件
                JarFile jarFile = jarURLConnection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();

                // 创建自定义类加载器，并加到map中方便管理
                URLClassLoader myClassloader = URLClassLoader.newInstance(new URL[] { url }, ClassLoader.getSystemClassLoader());
                Set<Class> initBeanClass = new HashSet<>(jarFile.size());
                // 遍历文件
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.getName().endsWith(".class")) {
                        // 1. 加载类到jvm中
                        // 获取类的全路径名
                        String className = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - 6);
                        // 1.1进行反射获取
                        try {
                            myClassloader.loadClass(className);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (IOException e) {
                // logger.error("读取{} 文件异常", fileName);
                throw new RuntimeException("读取jar文件异常: " + fileName);
            }
        }

    }
}