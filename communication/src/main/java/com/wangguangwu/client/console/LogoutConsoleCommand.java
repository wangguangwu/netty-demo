package com.wangguangwu.client.console;

import com.wangguangwu.protocol.response.LogoutResponsePacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangguangwu
 */
public class LogoutConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        channel.writeAndFlush(logoutResponsePacket);
    }
}
