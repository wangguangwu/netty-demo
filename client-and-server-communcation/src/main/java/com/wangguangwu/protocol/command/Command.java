package com.wangguangwu.protocol.command;

/**
 * @author wangguangwu
 */
public final class Command {

    /**
     * LOGIN_REQUEST
     */
    public static final Byte LOGIN_REQUEST = 1;

    /**
     * LOGIN_RESPONSE
     */
    public static final Byte LOGIN_RESPONSE = 2;

    /**
     * MESSAGE_REQUEST
     */
    public static final  Byte MESSAGE_REQUEST = 3;

    /**
     * MESSAGE_RESPONSE
     */
    public static final Byte MESSAGE_RESPONSE = 4;

    private Command() {
    }
}
