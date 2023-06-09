package com.wangguangwu.client;

import com.wangguangwu.client.handler.LoginResponseHandler;
import com.wangguangwu.client.handler.MessageResponseHandler;
import com.wangguangwu.codec.PacketDecoder;
import com.wangguangwu.codec.PacketEncoder;
import com.wangguangwu.codec.Splitter;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Scanner;
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
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("{}: connect success, start console thread", new Date());
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                log.error("retry times is exhausted, give up connection");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                log.info("{}: connect success, the [{}] time to reconnect", new Date(), order);
                bootstrap.config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    @SuppressWarnings("all")
    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    log.info("enter username to log in：");
                    String username = scanner.nextLine();
                    loginRequestPacket.setUsername(username);

                    // default password
                    loginRequestPacket.setPassword("123456");

                    // send login packet
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                } else {
                    String toUserId = scanner.next();
                    String message = scanner.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("error message: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
