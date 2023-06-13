package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangguangwu
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) {
        String fromUserId = responsePacket.getFromUserId();
        String fromUsername = responsePacket.getFromUsername();

        System.out.println(fromUserId + ":" + fromUsername + " -> " + responsePacket
                .getMessage());
    }
}
