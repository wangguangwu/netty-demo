package com.wangguangwu.server.handler;

import com.wangguangwu.protocol.request.CreateGroupRequestPacket;
import com.wangguangwu.protocol.response.CreateGroupResponsePacket;
import com.wangguangwu.util.IdUtil;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangguangwu
 */
@Slf4j
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        userIdList.forEach(userId -> {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });

        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IdUtil.randomId());
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        log.info("create group success, id: [{}]", createGroupResponsePacket.getGroupId());
        log.info("group has : {}", createGroupResponsePacket.getUserNameList());
        log.info("channelGroup: {}", channelGroup);
    }

}
