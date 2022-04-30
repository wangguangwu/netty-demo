package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangguangwu
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        SessionUtil.unBindSession(ctx.channel());
    }

}