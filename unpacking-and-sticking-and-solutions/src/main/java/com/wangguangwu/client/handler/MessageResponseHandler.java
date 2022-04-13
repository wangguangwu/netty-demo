package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) {
        log.info("{}: receive data from server -> {}", new Date(), msg.getMessage());
    }

}
