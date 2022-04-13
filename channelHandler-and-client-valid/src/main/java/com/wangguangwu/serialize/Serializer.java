package com.wangguangwu.serialize;

import com.wangguangwu.serialize.impl.JsonSerializer;

/**
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * get serializer algorithm
     *
     * @return serializer algorithm
     */
    byte getSerializerAlgorithm();

    /**
     * serialize
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);

    /**
     * deserialize
     *
     * @param clazz clazz
     * @param bytes bytes
     * @param <T>   <T>
     * @return java object
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
