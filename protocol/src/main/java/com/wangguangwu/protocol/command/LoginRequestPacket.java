package com.wangguangwu.protocol.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangguangwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
