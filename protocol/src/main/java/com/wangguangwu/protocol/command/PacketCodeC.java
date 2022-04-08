package com.wangguangwu.protocol.command;

import com.wangguangwu.serialize.Serializer;
import com.wangguangwu.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangguangwu
 */
@SuppressWarnings("unused")
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
        ByteBuf buffer = ByteBufAllocator.DEFAULT.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // to judge whether data compliant with custom protocol specification
        buffer.writeInt(MAGIC_NUMBER);
        // version, such as IPV4 or IPV^
        buffer.writeByte(packet.getVersion());
        // serializer algorithm
        buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        // command, corresponding to different processing logic
        buffer.writeByte(packet.getCommand());
        // data length
        buffer.writeInt(bytes.length);
        // data
        buffer.writeBytes(bytes);

        return buffer;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        byte serialAlgorithm = byteBuf.readByte();

        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serialAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
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
