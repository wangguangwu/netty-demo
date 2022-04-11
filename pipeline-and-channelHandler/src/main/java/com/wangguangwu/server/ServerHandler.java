package com.wangguangwu.server;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.resposne.LoginResponsePacket;
import com.wangguangwu.protocol.resposne.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
@SuppressWarnings("unused")
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket loginRequestPacket) {
            log.info("{}: receive client login request", new Date());
            // login process
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("{}: login success", new Date());
            } else {
                loginResponsePacket.setReason("account password verification failed");
                loginResponsePacket.setSuccess(false);
                log.error("{}: login failed", new Date());
            }

            // login response
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket messageRequestPacket) {
            // client send message
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            log.info("{}: receive client message: {}", new Date(), messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("Hello World");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        log.info("loginRequestPacket: {}", loginRequestPacket);
        return loginRequestPacket != null;
    }

}
