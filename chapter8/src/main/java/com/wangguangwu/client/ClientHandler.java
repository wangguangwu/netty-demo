package com.wangguangwu.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author wangguangwu
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 本方法在客户端连接建立之后被调用，我们在这编写向服务端写数据的逻辑
        // 1. 获取数据
        ByteBuf byteBuf = getByteBuf(ctx);

        // 2. 写入数据
        // 通过 channel 的 writeAndFlush 方法将数据写出到网络中，并且立即进行发送
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "Hello Server.".getBytes(StandardCharsets.UTF_8);

        // 调用 ctx.alloc() 方法获取到一个 ByteBuf 的内存管理器
        // 分配一个 ByteBuf
        ByteBuf buffer = ctx.alloc().ioBuffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
