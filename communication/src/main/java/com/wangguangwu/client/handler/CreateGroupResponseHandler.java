package com.wangguangwu.client.handler;

import com.wangguangwu.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) {
        log.info("create group success, id: [{}]", msg.getGroupId());
        log.info("group has {}", msg.getUserNameList());
    }

}
