package com.wangguangwu.handler;

import com.wangguangwu.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author wangguangwu
 */
public interface PacketHandler {

    void handle(Packet packet, ChannelHandlerContext ctx);

}
