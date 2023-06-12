package com.wangguangwu.client.console;

import com.google.common.collect.ImmutableMap;
import com.wangguangwu.client.console.impl.CreateGroupConsoleCommand;
import com.wangguangwu.client.console.impl.LogoutConsoleCommand;
import com.wangguangwu.client.console.impl.SendToUserConsoleCommand;
import com.wangguangwu.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Scanner;

/**
 * @author wangguangwu
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private final Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = ImmutableMap.of(
                "sendToUser", new SendToUserConsoleCommand(),
                "logout", new LogoutConsoleCommand(),
                "createGroup", new CreateGroupConsoleCommand()
        );
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        //  获取第一个指令
        String command = scanner.next();

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
