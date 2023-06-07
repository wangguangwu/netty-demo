package com.wangguangwu.server;

import com.wangguangwu.handler.PacketHandler;
import com.wangguangwu.manager.PacketHandlerManager;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangguangwu
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        PacketHandler handler = PacketHandlerManager.getHandler(packet);
        if (handler != null) {
            handler.handle(packet, ctx);
        }
    }
}
