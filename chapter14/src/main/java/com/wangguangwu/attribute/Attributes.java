package com.wangguangwu.attribute;

import com.wangguangwu.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author wangguangwu
 */
public final class Attributes {

    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    private Attributes() {
    }
}
