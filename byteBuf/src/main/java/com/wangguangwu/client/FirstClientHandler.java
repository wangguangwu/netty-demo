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
        log.info("{} 客户端写出数据", new Date());

        // 1. get data
        ByteBuf byteBuf = getByteBuf(ctx);

        // 2. write data
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        log.info("{} 客户端读到数据 -> {}", new Date(), byteBuf.toString(StandardCharsets.UTF_8));
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. get byteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. get data
        byte[] bytes = "Hello World".getBytes(StandardCharsets.UTF_8);

        // 3. fill data into byteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

}
