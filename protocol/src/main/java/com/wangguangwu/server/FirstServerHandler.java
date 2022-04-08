package com.wangguangwu.server;

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
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("{} 服务端读取到数据 -> {}", new Date(), byteBuf.toString(StandardCharsets.UTF_8));

        ByteBuf response = getResponse(ctx);
        log.info("{} 服务端回写数据 -> {}", new Date(), response.toString(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(response);

    }

    private ByteBuf getResponse(ChannelHandlerContext ctx) {
        byte[] bytes = "Hello Client".getBytes(StandardCharsets.UTF_8);

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }

}
