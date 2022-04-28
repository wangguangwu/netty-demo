package com.wangguangwu.protocol;

import lombok.Data;

/**
 * @author wangguangwu
 */
@Data
public abstract class Packet {

    private byte version = 1;

    /**
     * get command
     *
     * @return command
     */
    public abstract byte getCommand();

}
