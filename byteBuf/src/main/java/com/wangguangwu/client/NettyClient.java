package com.wangguangwu.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author wangguangwu
 */
@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // connect related logic processing chains
                        ch.pipeline()
                                // add a logical processor
                                .addLast(new FirstClientHandler());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("host: {}, port: {}, connect success", host, port);
            } else if (retry == 0) {
                log.info("the number of retries has been exhausted, connection is abandoned");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                // exponential Backoff
                int delay = 1 << order;
                log.info("host: {}, port: {}, connect failed, the {} time to reconnect", host, port, order);
                // get workerGroup
                bootstrap.config().group()
                        .schedule(
                                () -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS
                        );
            }
        });
    }

}
