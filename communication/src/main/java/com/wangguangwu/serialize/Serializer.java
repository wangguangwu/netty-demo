package com.wangguangwu.serialize;

import com.wangguangwu.serialize.impl.JsonSerializer;

/**
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * serializer algorithm
     *
     * @return byte
     */
    byte getSerializerAlgorithm();

    /**
     * convert java object to binary data
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);

    /**
     * convert binary data to java object
     *
     * @param clazz clazz
     * @param bytes binary data
     * @return java object
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
