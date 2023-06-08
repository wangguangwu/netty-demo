package com.wangguangwu.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author wangguangwu
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 本方法在客户端连接建立之后被调用，我们在这编写向服务端写数据的逻辑
        // 1. 获取数据
        ByteBuf byteBuf = getByteBuf(ctx);

        // 2. 写入数据
        // 通过 channel 的 writeAndFlush 方法将数据写出到网络中，并且立即进行发送
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 处理从网络上读取到的数据
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": client received -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "Hello Server.".getBytes(StandardCharsets.UTF_8);

        // 调用 ctx.alloc() 方法获取到一个 ByteBuf 的内存管理器
        // 分配一个 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
