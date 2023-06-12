package com.wangguangwu.client;

import com.wangguangwu.constant.AddressConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * @author wangguangwu
 */
public class Client {


    public static void main(String[] args) throws IOException {
        // 存储所有创建成功的 SocketChannel
        LinkedList<SocketChannel> clientList = new LinkedList<>();

        // 服务器的地址
        InetSocketAddress serverAddr = new InetSocketAddress(AddressConstant.IP, AddressConstant.PORT);

        // 在 20000 到 50000 范围内的端口上创建 SocketChannel，并尝试连接到服务器
        IntStream.range(20000, 50000).forEach(i -> {
            try {
                // 创建并绑定 SocketChannel
                SocketChannel client = SocketChannel.open();
                client.bind(new InetSocketAddress(AddressConstant.IP, i));
                // 尝试连接服务器
                client.connect(serverAddr);
                System.out.println("Client: [" + i + "] connected");
                // 将创建成功的 SocketChannel 添加到列表
                clientList.add(client);
            } catch (IOException e) {
                System.out.println("IOException" + i);
                e.printStackTrace();
            }
        });
        System.out.println("clients.size: " + clientList.size());

        // 阻塞主线程，等待从数据流中读取数据
        System.in.read();
    }
}
