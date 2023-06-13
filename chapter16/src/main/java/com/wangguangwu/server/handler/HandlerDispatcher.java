package com.wangguangwu.server.handler;

import com.google.common.collect.ImmutableMap;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author wangguangwu
 */
@ChannelHandler.Sharable
public class HandlerDispatcher extends SimpleChannelInboundHandler<Packet> {

    public static final HandlerDispatcher INSTANCE = Holder.INSTANCE;
    private static final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> PACKET_MAP;

    static {
        PACKET_MAP = ImmutableMap.<Byte, SimpleChannelInboundHandler<? extends Packet>>builder()
                .put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE)
                .put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE)
                .put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE)
                .put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE)
                .put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE)
                .put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE)
                .put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE)
                .build();
    }

    private static final class Holder {
        private static final HandlerDispatcher INSTANCE = new HandlerDispatcher();
    }

    private HandlerDispatcher() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        SimpleChannelInboundHandler<? extends Packet> handler = PACKET_MAP.get(packet.getCommand());
        if (handler != null) {
            handler.channelRead(ctx, packet);
        } else {
            throw new IllegalArgumentException("No handler for command: " + packet.getCommand());
        }
    }
}
