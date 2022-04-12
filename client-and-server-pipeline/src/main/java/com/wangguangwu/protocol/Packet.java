package com.wangguangwu.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wangguangwu
 */
@Data
public abstract class Packet {

    /**
     * protocol version
     */
    @JSONField(deserialize = false)
    private Byte version = 1;

    /**
     * get command
     *
     * @return command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
