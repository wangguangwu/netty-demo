package com.wangguangwu.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
@SuppressWarnings("unused")
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("{}: server receive data -> {}", new Date(), byteBuf.toString(StandardCharsets.UTF_8));
    }

}
