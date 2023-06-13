package com.wangguangwu.protocol.response;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import com.wangguangwu.session.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wangguangwu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
