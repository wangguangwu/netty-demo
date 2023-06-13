package com.wangguangwu.protocol.command;

/**
 * @author wangguangwu
 */
public final class Command {

    public static final Byte LOGIN_REQUEST = 1;
    public static final Byte LOGIN_RESPONSE = 2;
    public static final Byte MESSAGE_REQUEST = 3;
    public static final Byte MESSAGE_RESPONSE = 4;
    public static final Byte LOGOUT_REQUEST = 5;
    public static final Byte LOGOUT_RESPONSE = 6;
    public static final Byte CREATE_GROUP_REQUEST = 7;
    public static final Byte CREATE_GROUP_RESPONSE = 8;
    public static final Byte LIST_GROUP_MEMBERS_REQUEST = 9;
    public static final Byte LIST_GROUP_MEMBERS_RESPONSE = 10;
    public static final Byte JOIN_GROUP_REQUEST = 11;
    public static final Byte JOIN_GROUP_RESPONSE = 12;
    public static final Byte QUIT_GROUP_REQUEST = 13;
    public static final Byte QUIT_GROUP_RESPONSE = 14;

    private Command() {
    }
}
