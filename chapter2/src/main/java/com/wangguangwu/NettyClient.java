package com.wangguangwu;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangguangwu
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        // 创建用于处理 I/O 操作的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建客户端启动模型
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 绑定自定义属性到 channel
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                // 设置 TCP 底层属性
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3. 实现 IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        System.out.println("Client say: hello");
                    }
                });

        // 4. 建立连接
        connect(bootstrap, "127.0.0.1", 8088, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功!");
                    } else if (retry == 0) {
                        System.err.println("重试次数已用完，放弃连接！");
                    } else {
                        // 第几次重连
                        int order = (MAX_RETRY - retry) + 1;
                        // 设置重连时间
                        int delay = 1 << order;
                        System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                        // 使用 workerGroup 的 schedule 方法进行延时任务执行，实现连接失败后的自动重连
                        bootstrap.config()
                                .group()
                                .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
                    }
                });
    }
}
