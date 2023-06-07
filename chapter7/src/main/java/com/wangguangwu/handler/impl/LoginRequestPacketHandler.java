package com.wangguangwu.handler.impl;

import com.wangguangwu.handler.PacketHandler;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author wangguangwu
 */
public class LoginRequestPacketHandler implements PacketHandler {

    @Override
    public void handle(Packet packet, ChannelHandlerContext ctx) {
        LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
        System.out.println(new Date() + ": 收到客户端登录请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ": 登录成功!");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        // 登录响应
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket.getPassword() != null;
    }
}
