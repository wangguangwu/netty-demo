package com.wangguangwu.serialize;

import com.wangguangwu.serialize.impl.JsonSerializer;

/**
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 获取序列化算法
     *
     * @return serializerAlgorithm
     */
    Byte getSerializerAlgorithm();

    /**
     * 序列化
     *
     * @param obj java 对象
     * @return 二进制数据
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     *
     * @param clazz 类
     * @param bytes 二进制数据
     * @param <T>   java 对象对应的类
     * @return java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
