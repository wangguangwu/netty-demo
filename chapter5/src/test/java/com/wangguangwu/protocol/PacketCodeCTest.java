package com.wangguangwu.protocol;

import com.wangguangwu.protocol.packet.LoginRequestPacket;
import com.wangguangwu.protocol.packet.Packet;
import com.wangguangwu.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodeCTest {

    @Test
    public void testEncodeAndDecode() {
        JsonSerializer serializer = new JsonSerializer();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUserId(11);
        loginRequestPacket.setUsername("wang");
        loginRequestPacket.setPassword("123");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf encode = packetCodeC.encode(loginRequestPacket);
        Assert.assertNotNull(encode);

        Packet decode = packetCodeC.decode(encode);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decode));
    }
}