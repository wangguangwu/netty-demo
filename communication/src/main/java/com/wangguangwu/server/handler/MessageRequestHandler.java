package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {
        // 1. 拿到消息发送方的会话消息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        // 2. 通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3. 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4. 将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.error("[{}] is not online, send failed", messageRequestPacket.getToUserId());
        }
    }

}
