package com.wangguangwu.protocol;

import com.wangguangwu.protocol.command.Command;
import com.wangguangwu.protocol.request.LoginRequestPacket;
import com.wangguangwu.protocol.request.MessageRequestPacket;
import com.wangguangwu.protocol.response.LoginResponsePacket;
import com.wangguangwu.protocol.response.MessageResponsePacket;
import com.wangguangwu.serialize.Serializer;
import com.wangguangwu.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangguangwu
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    private static final Map<Byte, Serializer> SERIALIZER_MAP;
    public static final PacketCodeC INSTANCE = SingletonHolder.INSTANCE;

    static {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }

    private static class SingletonHolder {
        private static final PacketCodeC INSTANCE = new PacketCodeC();
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
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
