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
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) {
        // 1. get session
        Session session = SessionUtil.getSession(ctx.channel());

        // 2. message
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(messageResponsePacket.getMessage());

        // 3. receiver channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        // 4. send message
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.error("[{}] is not on line, send failed", msg.getToUserId());
        }
    }

}

