package com.wangguangwu.protocol.packet;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wangguangwu
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 获取命令
     *
     * @return command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
