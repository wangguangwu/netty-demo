package com.wangguangwu.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author wangguangwu
 */
public interface ConsoleCommand {

    void exec(Scanner scanner, Channel channel);

}
