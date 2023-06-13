package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangguangwu
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            System.out.println("登出成功");
        } else {
            System.out.println("登出失败");
        }
    }
}
