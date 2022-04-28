package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangguangwu
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        loginResponsePacket.setUsername(loginResponsePacket.getUsername());

        if (valid(msg)) {
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setSuccess(true);
            log.info("{}: [{}] login success", new Date(), loginResponsePacket.getUsername());
            SessionUtil.bindSession(new Session(userId, loginResponsePacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setReason("account password valid fail");
            loginResponsePacket.setSuccess(false);
            log.error("{}: login error", new Date());
        }

        // login failed
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }


    private boolean valid(LoginRequestPacket loginRequestPacket) {
        log.info("loginRequestPacket: {}", loginRequestPacket);
        return loginRequestPacket != null;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
