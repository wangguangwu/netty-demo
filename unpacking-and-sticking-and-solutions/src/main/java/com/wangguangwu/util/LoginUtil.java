package com.wangguangwu.util;

import com.wangguangwu.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author wangguangwu
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr != null;
    }

    private LoginUtil() {
    }

}
