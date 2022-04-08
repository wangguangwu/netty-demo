package com.wangguangwu.client;

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
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = getMessage(ctx);
        log.info("{} 客户端写出数据 -> {}", new Date(), message.toString(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) {
        ByteBuf response = (ByteBuf) message;
        log.info("{} 客户端读取到数据 -> {}", new Date(), response.toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getMessage(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = "Hello Server".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
        return buffer;
    }

}
