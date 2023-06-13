package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.ListGroupMembersRequestPacket;
import com.wangguangwu.protocol.response.ListGroupMembersResponsePacket;
import com.wangguangwu.session.Session;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangguangwu
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {
        // 1. 获取群的 ChannelGroup
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 遍历群成员的 channel 对应的 session，构建群成员的信息
        List<Session> sessionList = channelGroup.stream()
                .map(SessionUtil::getSession)
                .collect(Collectors.toList());

        // 3. 构建获取成员列表响应回写到客户端
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
