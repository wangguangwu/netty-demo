package com.wangguangwu.codec;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wangguangwu
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        PacketCodeC.INSTANCE.encode(byteBuf, packet);
    }

}
