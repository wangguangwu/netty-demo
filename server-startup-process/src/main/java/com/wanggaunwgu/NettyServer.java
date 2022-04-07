package com.wanggaunwgu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * Server startup process.
 * <p>
 * three parameters:
 * 1. thread model
 * 2. IO model
 * 3. connection read and write processing logic
 *
 * @author wangguangwu
 */
@Slf4j
public class NettyServer {

    private static final int BEGIN_PORT = 1000;

    public static void main(String[] args) {
        // listen port, accept new connection
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // handle with connection
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // create a guide class
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        final AttributeKey<Object> serverKey = AttributeKey.newInstance("serverName");
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                // configure thread group
                .group(bossGroup, workerGroup)
                // specify the io model
                .channel(NioServerSocketChannel.class)
                // specify some attributes
                .attr(serverKey, "nettyServer")
                // the maximum length of the queue that temporarily stores requests that have completed the three-way handshake
                .option(ChannelOption.SO_BACKLOG, 1024)
                // enable the underlying heartbeat mechanism of TCP
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // turn on Nagle's algorithm
                //
                .childOption(ChannelOption.TCP_NODELAY, true)
                // handle server running
                // If high real-time performance is required, it is sent immediately when there is data to be sent, and it is turned off.
                // If it is necessary to reduce the number of transmissions and reduce network interaction, turn it on.
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) {
                        // get attribute
                        log.info("{} is running....", ch.attr(serverKey).get());
                    }
                })
                // define data handle for each connection
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        log.info((String) ch.attr(clientKey).get());
                    }
                });

        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        // bind() will return a channelFuture immediately
        // add a listener to handle with return value
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("port [{}] binding success", port);
            } else {
                log.error("port [{}] binding failed", port);
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
