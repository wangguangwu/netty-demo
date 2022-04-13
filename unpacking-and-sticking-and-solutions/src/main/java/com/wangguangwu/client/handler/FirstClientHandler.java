package com.wangguangwu.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wangguangwu
 */
@SuppressWarnings("unused")
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        int times = 1000;
        for (int i = 0; i < times; i++) {
            ByteBuf buffer = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buffer);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "很高兴认识你".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

}
