package com.wangguangwu.util;

import java.util.UUID;

/**
 * @author wangguangwu
 */
public final class IdUtil {

    private IdUtil() {
    }

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
