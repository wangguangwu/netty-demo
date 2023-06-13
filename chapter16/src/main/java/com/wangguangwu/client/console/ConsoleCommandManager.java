package com.wangguangwu.client.console;

import com.google.common.collect.ImmutableMap;
import com.wangguangwu.client.console.impl.*;
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
        consoleCommandMap = ImmutableMap.<String, ConsoleCommand>builder()
                .put("sendToUser", new SendToUserConsoleCommand())
                .put("logout", new LogoutConsoleCommand())
                .put("createGroup", new CreateGroupConsoleCommand())
                .put("joinGroup", new JoinGroupConsoleCommand())
                .put("quitGroup", new QuitGroupConsoleCommand())
                .put("listGroupMembers", new ListGroupMembersConsoleCommand())
                .put("sendToGroup", new SendToGroupConsoleCommand())
                .build();
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
