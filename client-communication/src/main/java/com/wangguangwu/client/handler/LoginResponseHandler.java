package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        String userId = msg.getUserId();
        String username = msg.getUsername();

        if (msg.isSuccess()) {
            log.info("[{}] login success, userId is [{}]", username, userId);
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            log.error("[{}] login error, reason is [{}]", username, msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("client connection been closed");
    }

}
