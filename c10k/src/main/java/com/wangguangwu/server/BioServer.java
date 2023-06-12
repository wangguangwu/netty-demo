package com.wangguangwu.server;

import com.wangguangwu.constant.AddressConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO Server
 * <p>
 * 每次接收到一个客户端连接，就会创建一个线程，每个线程绑定一个客户端。
 * <p>
 * BIO 的核心问题：
 * <p>
 * 阻塞导致多线程
 *
 * @author wangguangwu
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(AddressConstant.PORT, 20);
        System.out.println("Sever start");
        while (true) {
            // 等待线程连接
            Socket client = server.accept();
            System.out.println("accept client: " + client.getPort());
            new Thread(() -> {
                InputStream in;
                try {
                    in = client.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    while (true) {
                        // 读取数据
                        String data = reader.readLine();
                        if (data != null && !"".equals(data)) {
                            System.out.println(data);
                        } else {
                            client.close();
                            break;
                        }
                    }
                    System.out.println("Client break");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
