package com.wangguangwu;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Client startup process.
 * three parameters
 * <p>
 * 1. thread model
 * 2. IO model
 * 3. business logic processing
 *
 * @author wangguangwu
 */
@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientName");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. specify thread model
                .group(workerGroup)
                // 2. specify NIO type
                .channel(NioSocketChannel.class)
                .attr(clientKey, "nettyClient")
                // set tcp low-level properties
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3. business logic processing
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        log.info("{} connecting", ch.attr(clientKey).get());
                    }
                });

        // connect server
        connect(bootstrap, "127.0.0.1", 8080, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("host: {}, port:{}, connect success", host, port);
            } else if (retry == 0) {
                log.info("the number of retries has been exhausted, connection is abandoned");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                log.info("host: {}, port:{}, connect failed, the {} time to reconnect", host, port, order);
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
