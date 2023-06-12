package com.wangguangwu.server;

import com.wangguangwu.constant.AddressConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * NIO 如何解决 BIO 阻塞的问题？
 * <p>
 * 非阻塞 + 少量线程。
 *
 * @author wangguangwu
 */
public class NioServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<SocketChannel> clientList = new LinkedList<>();
        // 服务器开启监听
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(AddressConstant.PORT));
        // 设置操作系统级别为非阻塞 NONBLOCKING
        serverSocketChannel.configureBlocking(false);
        while (true) {
            // 接受客户端连接
            TimeUnit.SECONDS.sleep(1);
            /*
            accept 调用了内核
            在设置操作系统级别为非阻塞的情况下
            若有客户端连接，直接返回客户端
            若无客户端连接。则返回 null
            设置成 NONBLOCKING 后，代码不阻塞，线程不挂起，继续往下执行
             */
            SocketChannel client = serverSocketChannel.accept();
            // 接收到客户端连接
            if (client != null) {
                // 设置客户端读写数据为非阻塞
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client port: " + port);
                clientList.add(client);
            }
            // 设置堆外内存
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            // 遍历所有客户端
            for (SocketChannel channel : clientList) {
                // 读取数据，不阻塞
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    String str = new String(bytes);
                    System.out.println(channel.socket().getPort() + " : " + str);
                    buffer.clear();
                }
            }
        }
    }
}
