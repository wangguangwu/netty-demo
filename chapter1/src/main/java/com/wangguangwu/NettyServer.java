package com.wangguangwu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * 这是一个 Netty 服务器的基本实现。
 *
 * @author wangguangwu
 */
public class NettyServer {

    private static final int LISTEN_PORT = 8088;

    public static void main(String[] args) {
        // 监听端口，接收新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理每一条连接的数据读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 创建一个名为 AttributeKey，用于保存一些特殊的属性
        AttributeKey<Object> serverKey = AttributeKey.newInstance("serverName");
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                // 配置线程组
                .group(bossGroup, workerGroup)
                // 指定 IO 模型为 NIO
                .channel(NioServerSocketChannel.class)
                // 为服务端通道设置属性
                .attr(serverKey, "nettyServer")
                // 为每一个接入的客户端连接设置属性
                .childAttr(clientKey, "clientValue")
                // 设置服务端 TCP 参数，backlog 参数设置指定了内核为此套接字排队的最大连接个数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 设置后续连接的参数
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 设置服务端启动过程中的逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("Server: " + ch.attr(serverKey).get());
                    }
                })
                // 定义后续每条连接的读写逻辑处理
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            protected void initChannel(NioSocketChannel ch) {
                                System.out.println("Client: " + ch.attr(clientKey).get());
                            }
                        }
                );

        bind(serverBootstrap, LISTEN_PORT);
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
}
