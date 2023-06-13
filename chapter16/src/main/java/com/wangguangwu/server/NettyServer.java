package com.wangguangwu.server;

import com.wangguangwu.codec.PacketCodecHandler;
import com.wangguangwu.codec.Splitter;
import com.wangguangwu.server.handler.AuthHandler;
import com.wangguangwu.server.handler.HandlerDispatcher;
import com.wangguangwu.server.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wangguangwu
 */
public class NettyServer {

    private static final int PORT = 8088;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new MyChannelInitializer());

        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }

    private static class MyChannelInitializer extends ChannelInitializer<NioSocketChannel> {
        @Override
        protected void initChannel(NioSocketChannel ch) {
            // 拆包器
            // 内部实现与每个 channel 有关，需要维护每个 channel 当前读到的数据，是有状态的
            ch.pipeline().addLast(new Splitter());

            ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
            // 登录请求处理器
            ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
            ch.pipeline().addLast(AuthHandler.INSTANCE);
            // 其他处理器
            ch.pipeline().addLast(HandlerDispatcher.INSTANCE);
        }
    }
}
