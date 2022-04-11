package com.wangguangwu.server;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket loginRequestPacket) {
            log.info("{}: receive client login request", new Date());
            // client login
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("{}: login success", new Date());
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("account password verification failed");
                log.error("{}: login failed", new Date());
            }

            // login response
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        } else if (packet instanceof MessageRequestPacket messageRequestPacket) {
            // client send message
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            log.info("{}: receive client message: {}", new Date(), messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("Hello World");
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(response);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        String username = loginRequestPacket.getUsername();
        String password = loginRequestPacket.getPassword();
        return "wangguangwu".equals(username) && "123456".equals(password);
    }

}
