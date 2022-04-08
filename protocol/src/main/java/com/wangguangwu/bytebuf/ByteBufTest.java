package com.wangguangwu.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        buffer.writeBytes(new byte[]{5});
        print("writeByte(5)", buffer);

        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        log.info("getByte(3) return: " + buffer.getByte(3));
        log.info("getShort(3) return: " + buffer.getShort(3));
        log.info("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

    }

    private static void print(String action, ByteBuf buffer) {
        log.info("after================[{}]=====================", action);
        log.info("capacity: {}", buffer.capacity());
        log.info("maxCapacity: {}", buffer.maxCapacity());
        log.info("readerIndex: {}", buffer.readerIndex());
        log.info("readableBytes: {}", buffer.readableBytes());
        log.info("isReadable: {}", buffer.isReadable());
        log.info("writerIndex: {}", buffer.writerIndex());
        log.info("writableBytes: {}", buffer.writableBytes());
        log.info("isWritable: {}", buffer.isWritable());
        log.info("maxWriteableBytes: {}", buffer.maxWritableBytes());
        log.info("==============================================");
    }

}
