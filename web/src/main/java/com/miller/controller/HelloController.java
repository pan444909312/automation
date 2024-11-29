package com.miller.controller;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.clz.ClassFindService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO 描述
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/6 17:45:14
 */
@RequestMapping("/test")
@RestController
public class HelloController {

    @Resource
    private ClassFindService classFindService;

    @PostMapping("/getPackageClass")
    public void getPackageClass(@RequestParam(value = "packagePath") String packagePath) {
        // 获取包下所有类, 通过注解过滤
        List<Class<?>> packageClass = classFindService.getPackageClass(packagePath)
                .stream().filter(clz -> clz.isAnnotationPresent(Scenario.class)).toList();
        System.out.println(packageClass.size());
        packageClass.forEach(System.out::println);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
