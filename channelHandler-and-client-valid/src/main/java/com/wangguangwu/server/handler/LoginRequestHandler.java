package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.util.LoginUtil;
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
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) {
        log.info("{}: receive client login request", new Date());

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            log.info("{}: login success", new Date());
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("account password valid failed");
            log.error("{}: login failed", new Date());
        }

        // login response
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        log.info("loginRequestPacket: {}", loginRequestPacket);
        return loginRequestPacket != null;
    }

}
