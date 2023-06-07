package com.wangguangwu.manager;

import com.wangguangwu.handler.PacketHandler;
import com.wangguangwu.handler.impl.LoginRequestPacketHandler;
import com.wangguangwu.handler.impl.LoginResponsePacketHandler;
import com.wangguangwu.handler.impl.MessageRequestPacketHandler;
import com.wangguangwu.handler.impl.MessageResponsePacketHandler;
import com.wangguangwu.protocol.Packet;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangguangwu
 */
public class PacketHandlerManager {

    private static class HandlerHolder {
        private static final Map<Class<? extends Packet>, PacketHandler> HANDLER_MAP = createMap();

        private static Map<Class<? extends Packet>, PacketHandler> createMap() {
            Map<Class<? extends Packet>, PacketHandler> map = new HashMap<>(4);
            map.put(LoginRequestPacket.class, new LoginRequestPacketHandler());
            map.put(LoginResponsePacket.class, new LoginResponsePacketHandler());
            map.put(MessageRequestPacket.class, new MessageRequestPacketHandler());
            map.put(MessageResponsePacket.class, new MessageResponsePacketHandler());
            return Collections.unmodifiableMap(map);
        }
    }

    private PacketHandlerManager() {
    }

    public static PacketHandler getHandler(Packet packet) {
        return HandlerHolder.HANDLER_MAP.get(packet.getClass());
    }
}
