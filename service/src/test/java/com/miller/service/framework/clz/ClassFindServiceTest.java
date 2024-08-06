package com.miller.service.framework.clz;

import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ClassFindService.class)
class ClassFindServiceTest {

    @Autowired
    private ClassFindService classFindService;

    @Test
    void getPackageClass() {
        // 获取包下所有类, 通过注解过滤
        List<Class<?>> packageClass = classFindService.getPackageClass("com.miller.service.framework.annotation")
                .stream().filter(clz -> clz.isAnnotationPresent(Scenario.class)).toList();
        System.out.println(packageClass.size());
        packageClass.forEach(System.out::println);
    }
}