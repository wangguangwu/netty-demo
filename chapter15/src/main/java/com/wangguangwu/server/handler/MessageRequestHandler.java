package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangguangwu
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket) {
        // 1. 拿到消息发送方的会话消息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2. 通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUsername(session.getUsername());
        messageResponsePacket.setMessage(requestPacket.getMessage());

        // 3. 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(requestPacket.getToUserId());

        // 4. 将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + requestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
