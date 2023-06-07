package com.wangguangwu.handler.impl;

import com.wangguangwu.handler.PacketHandler;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author wangguangwu
 */
public class MessageResponsePacketHandler implements PacketHandler {

    @Override
    public void handle(Packet packet, ChannelHandlerContext ctx) {
        MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
    }
}
