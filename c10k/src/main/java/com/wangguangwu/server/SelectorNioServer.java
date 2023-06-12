package com.wangguangwu.server;

import com.wangguangwu.constant.AddressConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用
 *
 * @author wangguangwu
 */
public class SelectorNioServer {

    private Selector selector = null;

    public static void main(String[] args) {
        new SelectorNioServer().start();
    }

    public void initServer() {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(AddressConstant.PORT));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        while (true) {
            try {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("可处理事件数量：" + keys.size());
                while (selector.select() > 0) {
                    // 返回待处理的文件描述符集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            // 客户端连接设置为非阻塞
            client.configureBlocking(false);
            // 设置堆外内存
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("client connected: " + client.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        try {
            // 可读事件
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            int len = socketChannel.read(buffer);
            System.out.println("readHandler len: " + len);
            if (len > 0) {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String message = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("Received message from " + socketChannel.getRemoteAddress() + ": " + message);
                // 向客户端发送响应
                String response = "Hello client";
                ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                socketChannel.write(responseBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
