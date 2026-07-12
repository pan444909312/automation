package com.miller.common.util;

import com.github.f4b6a3.ulid.UlidCreator;

/**
 * ULID Tools, 用于替换 UUID 的一种方案
 *
 * @author Miller Shan
 * @see <a href="https://www.crockford.com/base32.html">ULID</a>
 * @since 2023/09/28 11:17:07
 */
public class ULIDUtils {
    /**
     * 生成带排序的 ULID
     *
     * @return 26位的 ULID like {@code 01GK396NT61RY779VWJTG413JA}
     */
    public static String generateULID() {
        var monotonicUlid = UlidCreator.getMonotonicUlid();
        return monotonicUlid.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 300; i++) {
            System.out.println(generateULID());
        }
    }
}
