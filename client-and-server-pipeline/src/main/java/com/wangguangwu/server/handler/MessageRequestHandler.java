package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) {
        log.info("{}: receive client message: {}", new Date(), msg.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setVersion(msg.getVersion());
        messageResponsePacket.setMessage("Hello World");

        // message response
        ctx.channel().writeAndFlush(messageResponsePacket);
    }

}
