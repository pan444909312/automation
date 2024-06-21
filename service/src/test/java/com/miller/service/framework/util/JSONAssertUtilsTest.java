package com.miller.service.framework.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <ul>
 *     JSON 比较模式
 *     <li>
 *          1. JSONCompareMode.LENIENT: 宽松模式，如果实际JSON字符串包含额外的字段，测试将仍然通过；
 *     </li>
 *     <li>
 *         2. JSONCompareMode.STRICT: 严格模式，如果实际JSON字符串包含额外的字段，测试将失败；
 *     </li>
 *     <li>
 *         3. JSONCompareMode.STRICT_ORDER: 严格顺序模式，如果实际JSON字符串包含额外的字段，测试将失败，并且如果字段顺序不同，测试将失败。
 *     </li>
 *     <li>
 *         4. JSONCompareMode.NON_EXTENSIBLE: 严格模式，如果实际JSON字符串包含额外的字段，测试将失败，并且如果字段顺序不同，测试将失败。
 *     </li>
 * </ul>
 */
class JSONAssertUtilsTest {
    @Test
    @DisplayName("The test will pass as the expected JSON string, and the actual JSON string are the same")
    void testForStrictMode() throws JSONException {
        String actual = "{id:123, name:\"John\"}";
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual,
                // The comparison mode LENIENT means that even if the actual JSON contains extended fields, the test will still pass
                JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("The test will pass, because we use assert not equals.")
    void testForStrictModeExcludeId() throws JSONException {
        String actual = "{id:123, name:\"John\"}";
        JSONAssert.assertNotEquals("{name:\"John\"}", actual, JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("As we can see, the real variable contains an additional field zip which is not present in the expected String. Still, the test will pass.")
    void testForLenientMode() throws JSONException {
        String actual = "{id:123, name:\"John\", zip:\"33025\"}";
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);
    }

    @Test
    @DisplayName("测试实际字符串为预期字符串的子集,忽略顺序")
    void testForLenientModeExcludeId() throws JSONException {
        String actual = "{id: 123, name: \"John\", zip: 33025}";
        // 实际项目中一般会忽略id字段
        JSONAssert.assertEquals("{zip: 33025, name:\"John\"}", actual, JSONCompareMode.LENIENT);
    }

    @Test
    @DisplayName("The value is same, bug value type different.")
    void testLogicalComparison() throws JSONException {
        JSONObject expected = new JSONObject();
        JSONObject actual = new JSONObject();
        expected.put("id", Integer.valueOf(12345));
        actual.put("id", Double.valueOf(12345));
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("Testing for Nested object")
    void testNestedObject() throws JSONException {
        var actual = "{id:1,name:\"Miller\", address:{city:\"Hollywood\", state:\"LA\", zip:91601}}";
        var expected = "{id:1,name:\"Miller\", address:{city:\"Hollywood\", state:\"LA\", zip:91601}}";
        JSONAssert.assertEquals(expected, actual, false);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(expected, actual, true);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("Testing for assert message")
    void testAssertMessage() throws JSONException {
        String actual = "{id:123,name:\"John\"}";
        String failureMessage = "Only one field is expected: name";
        try {
            JSONAssert.assertEquals(failureMessage, "{name:\"John\"}", actual, JSONCompareMode.STRICT);
        } catch (AssertionError ae) {
            System.out.println("AssertionError: " + ae.getMessage());
            assertThat(ae.getMessage()).containsIgnoringCase(failureMessage);
        }
    }

    @Test
    @DisplayName("The order of the Elements in an Array")
    void testComparisonOfArray() throws JSONException {
        String result = "[11, 22, 33, 44]";
        //对于 LENIENT 比较模式，顺序并不重要
        JSONAssert.assertEquals("[22, 33, 11, 44]", result, JSONCompareMode.LENIENT);

        // 在 STRICT 比较模式下，数组中元素的顺序必须完全相同。 一般用于验证返回结果的排序是否符合预期
        JSONAssert.assertEquals("[11, 22, 33, 44]", result, JSONCompareMode.STRICT);

        // 断言不相等
        JSONAssert.assertNotEquals("[44, 33, 22, 11]", result, JSONCompareMode.STRICT);
    }

    @Test
    @DisplayName("Elements of Array must be equals totally")
    void testElementsMustEquals() throws JSONException {
        String result = "[1,2,3,4,5]";
        JSONAssert.assertEquals("[1,2,3,4,5]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3,4,5,6]", result, JSONCompareMode.LENIENT);
    }

    @Test
    @DisplayName("Assert Array size")
    void assertArraySize() throws JSONException {
        String names = "{names:[Miller, Vicky, Mila, Millie]}";
        // 通过特殊的语法校验
        JSONAssert.assertEquals("{names:[4]}", names, new ArraySizeComparator(JSONCompareMode.LENIENT));
    }

    @Test
    @DisplayName("Assert Array range")
    void assertArraySizeRange() throws JSONException {
        String ratings = "{ratings:[3.2, 3.5, 4.1, 5, 1]}";
        // 验证数组中的所有元素的值必须在 [1,5] 之间，包括 1 和 5
        JSONAssert.assertEquals("{ratings:[1,5]}", ratings, new ArraySizeComparator(JSONCompareMode.LENIENT));
    }

    @Test
    @DisplayName("Assert Array by Regular")
    void assertArrayByRegular() throws JSONException {
        // 这里的 ID 可以是任意值
        JSONAssert.assertEquals("{entry:{id: [1, 3, 5, 7, 9, \"Miller\"]}}", "{entry:{id:1, id:2, id:6}}",
                // 使用自定义比较器
                new CustomComparator(JSONCompareMode.STRICT, new Customization("entry.id",
                        // 通过正则匹配。只要是数字即可
                        new RegularExpressionValueMatcher<Object>("\\d"))));
        // 断言不相等，因为id不仅仅是数字
        JSONAssert.assertNotEquals("{entry:{id:\"There are everything...\"}}", "{entry:{id:1, id:as}}",
                // 自定义比较器
                new CustomComparator(JSONCompareMode.STRICT, new Customization("entry.id",
                        // 正则匹配
                        new RegularExpressionValueMatcher<Object>("\\d"))));

    }
}