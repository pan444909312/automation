package com.miller.service.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 反射工具
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/18 15:13:22
 */
@Slf4j
public class ReflectionUtils {

    /**
     * 通过反射调用方法
     *
     * @param className      类全名。比如: com.xxx.xxx.Hello
     * @param methodName     方法名，不带括号。比如: methodName
     * @param args           方法参数，无惨传 null 即可。
     *                       比如: methodName(),则 args 传 null 或 new Object[]{};
     *                       methodName(String name), 则 args 传 new Object[]{"Miller"}, 并且 parameterTypes 参数 传 String.class
     * @param parameterTypes 方法参数的类型，方法有参数时必传。方法参数类型必须匹配，用于区分重载的方法。
     *                       比如: methodName(String name, Integer age) 则 parameterTypes 传String.class, Integer.class
     * @return 方法执行返回值
     */
    public static Object invokeMethod(String className, String methodName, Object[] args, Class<?>[] parameterTypes) {
        // 方法名称如果包含()则去掉
        if (methodName.endsWith("()")) methodName = methodName.substring(0, methodName.length() - 2);
        Object returnValue = null;
        try {
            Class<?> aClass = Class.forName(className);
            Method method = null;
            // 通过注解的方式传调用。注解中的参数只支持“常量”类型，所以通过注解调用方法时不传实参。
            if (Objects.isNull(args) || args.length < 1) {
                method = aClass.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                // 方法实参为空，使用占位符传递，否则会出现参数不匹配
                returnValue = method.invoke(aClass.newInstance(), new Object[parameterTypes.length]);
            }
            // 通过反射工具类正常调用时按照正常参数值、参数类型传值即可。
            else {
                method = aClass.getDeclaredMethod(methodName, parameterTypes);
                // 压制 JVM 对修饰符的检查，这样可以调用 private 方法。
                method.setAccessible(true);
                returnValue = method.invoke(aClass.newInstance(), args);
            }
        } catch (ClassNotFoundException e) {
            log.error("Class not found: {}", className);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            log.error("Method not found: {}", methodName);
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    /**
     * @see #invokeMethod(String, String, Object[], Class[])
     */
    public static Object invokeMethod(Class<?> className, String methodName, Object[] args, Class<?>[] parameterTypes) {
        return invokeMethod(className.getName(), methodName, args, parameterTypes);
    }

    /**
     * 调用无参方法
     *
     * @see #invokeMethod(String, String, Object[], Class[])
     */
    public static Object invokeMethodOfNoParameter(Class<?> className, String methodName) {
        return invokeMethod(className.getName(), methodName, new Object[]{}, new Class[]{});
    }

    /**
     * 调用无参方法
     *
     * @see #invokeMethod(String, String, Object[], Class[])
     */
    public static Object invokeMethodOfNoParameter(String className, String methodName) {
        return invokeMethod(className, methodName, new Object[]{}, new Class[]{});
    }
}
