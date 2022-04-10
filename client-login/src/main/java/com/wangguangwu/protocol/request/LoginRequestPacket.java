package com.wangguangwu.protocol.request;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangguangwu
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
