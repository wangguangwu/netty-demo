package com.wangguangwu.util;

import com.wangguangwu.attribute.Attributes;
import io.netty.channel.Channel;

/**
 * @author wangguangwu
 */
public final class LoginUtil {

    private LoginUtil() {
    }

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }
}
