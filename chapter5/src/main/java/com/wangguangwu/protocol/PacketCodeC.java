package com.wangguangwu.protocol;

import com.wangguangwu.protocol.command.Command;
import com.wangguangwu.protocol.packet.LoginRequestPacket;
import com.wangguangwu.protocol.packet.Packet;
import com.wangguangwu.serialize.Serializer;
import com.wangguangwu.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * 通信协议
 * <p>
 * 什么是协议？
 * <p>
 * 客户端和服务端双方约定好，双方沟通的基础。
 * <p>
 * 通信协议的构成
 * <p>
 * 1. 魔数：用于判断数据包是否符合协议
 * 2. 版本号：类似于 TCP 协议中的一个字段标识是 IPV4 协议还是 IPV6 协议
 * 3. 序列号算法：java 对象转二进制数据和二进制数据转 java 对象
 * 4. 指令
 * 5. 数据长度
 * 6. 数据
 *
 * @author wangguangwu
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    private static final Map<Byte, Serializer> SERIALIZER_MAP;

    static {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 获取序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 获取指令
        byte command = byteBuf.readByte();

        // 获取数据包长度
        int length = byteBuf.readInt();

        // 获取数据包
        byte[] content = new byte[length];
        byteBuf.readBytes(content);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, content);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return PACKET_TYPE_MAP.get(command);
    }
}
