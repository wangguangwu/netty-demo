package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.IdUtil;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author wangguangwu
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = Holder.INSTANCE;

    private static class Holder {
        private static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    }

    private LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        if (valid(requestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.randomId();
            String username = requestPacket.getUsername();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUsername(username);
            System.out.println("[" + username + "]登录成功");
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket.getPassword() != null;
    }
}
