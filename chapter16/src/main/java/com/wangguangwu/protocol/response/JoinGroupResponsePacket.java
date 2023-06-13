package com.wangguangwu.protocol.response;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangguangwu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JoinGroupResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
