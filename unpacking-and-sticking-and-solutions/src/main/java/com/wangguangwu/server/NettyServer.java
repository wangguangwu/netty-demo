package com.wangguangwu.server;

import com.wangguangwu.codec.PacketDecoder;
import com.wangguangwu.codec.PacketEncoder;
import com.wangguangwu.codec.Splitter;
import com.wangguangwu.server.handler.LoginRequestHandler;
import com.wangguangwu.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
@SuppressWarnings("all")
public class NettyServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new FirstServerHandler());
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("{}: port [{}] binding success", new Date(), port);
            } else {
                log.error("{}: port [{}] binding failed", new Date(), port);
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
