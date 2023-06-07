package com.wangguangwu.handler.impl;

import com.wangguangwu.handler.PacketHandler;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author wangguangwu
 */
public class LoginResponsePacketHandler implements PacketHandler {

    @Override
    public void handle(Packet packet, ChannelHandlerContext ctx) {
        LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

        if (loginResponsePacket.isSuccess()) {
            System.out.println(new Date() + ": 客户端登录成功");
            // 设置为登录成功
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因: " + loginResponsePacket.getReason());
        }
    }
}
