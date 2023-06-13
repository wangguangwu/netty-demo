package com.wangguangwu.protocol.response;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wangguangwu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private List<String> usernameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
