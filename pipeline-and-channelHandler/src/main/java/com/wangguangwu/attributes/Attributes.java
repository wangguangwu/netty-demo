package com.wangguangwu.attributes;

import io.netty.util.AttributeKey;

/**
 * @author wangguangwu
 */
public final class Attributes {

    public static final AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    private Attributes() {
    }

}
