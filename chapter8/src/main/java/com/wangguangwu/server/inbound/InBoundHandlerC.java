package com.wangguangwu.server.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wangguangwu
 */
public class InBoundHandlerC extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerC: " + msg);

        // 执行写操作，触发 ChannelOutboundHandlerAdapter 的事件
        ctx.channel().writeAndFlush(msg);
    }
}
