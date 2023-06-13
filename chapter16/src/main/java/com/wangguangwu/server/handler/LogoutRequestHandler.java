package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.LogoutRequestPacket;
import com.wangguangwu.protocol.response.LogoutResponsePacket;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangguangwu
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = Holder.INSTANCE;

    private static final class Holder {
        private static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();
    }

    private LogoutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket requestPacket) {
        // 解绑
        SessionUtil.unBindSession(ctx.channel());

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
