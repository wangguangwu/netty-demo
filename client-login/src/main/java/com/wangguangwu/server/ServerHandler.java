package com.wangguangwu.server;

import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.PacketCodeC;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
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
        log.info("{}：客户端开始登录", new Date());
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginRequestPacket loginRequestPacket) {
            // login
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("{}：登录成功", new Date());
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                log.error("{}：登录失败", new Date());
            }
            // login response
            ByteBuf response = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(response);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        log.info("loginRequestPacket: {}", loginRequestPacket);
        return loginRequestPacket != null;
    }

}
