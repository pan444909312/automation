package com.miller.testcaseuserapp.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;


class PandaTestDBHelpfulTest {

    @Test
    void testSelectOneSql() {
        Map<String, Object> map = PandaTestDBHelpful.executeSelectOneSql("select * from user limit 1");
        assertThatJson(map.get("user_id")).isNumber();
    }

    @Test
    void testSelectListSql() {
        List<Map<String, Object>> map = PandaTestDBHelpful.executeSelectListSql("select * from user limit 2");
        map.stream().findFirst().ifPresent(System.out::println);
        map.forEach(System.out::println);
        assertThatJson(map).isArray().hasSizeGreaterThan(1);
    }
}