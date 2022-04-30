package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.IdUtil;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.randomId();
            loginResponsePacket.setUserId(userId);
            log.info("[{}] login success", loginRequestPacket.getUserName());
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), channelHandlerContext.channel());
        } else {
            loginResponsePacket.setReason("account password valid failed");
            loginResponsePacket.setSuccess(false);
            log.error("{}: login failed", new Date());
        }

        // login response
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        log.debug("loginRequestPacket: {}", loginRequestPacket);
        return loginRequestPacket != null;
    }

}
