package com.wangguangwu.protocol.request;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wangguangwu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
