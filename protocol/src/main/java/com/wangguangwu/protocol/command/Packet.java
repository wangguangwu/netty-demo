package com.wangguangwu.protocol.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Java objects during communication
 *
 * @author wangguangwu
 */
@Data
public abstract class Packet {

    /**
     * protocol version
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    /**
     * get Command
     *
     * @return command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
