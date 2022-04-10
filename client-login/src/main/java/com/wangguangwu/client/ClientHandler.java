package com.wangguangwu.client;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * @author wangguangwu
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("{}：客户端开始登录", new Date());

        // create login object
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("wangguangwu");
        loginRequestPacket.setPassword("123456");

        // encode
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // write data
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket loginResponsePacket) {
            if (loginResponsePacket.isSuccess()) {
                log.info("{}：客户端登录成功", new Date());
            } else {
                log.error("{}：客户端登录失败，原因：{}", new Date(), loginResponsePacket.getReason());
            }
        }
    }

}
