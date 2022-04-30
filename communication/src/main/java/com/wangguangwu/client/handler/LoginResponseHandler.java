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
        String userName = msg.getUserName();

        if (msg.isSuccess()) {
            log.info("[{}]登录成功，userId 为: {}", userName, msg.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            log.info("[{}]登录失败，原因：{}", userName, msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端连接被关闭!");
    }

}
