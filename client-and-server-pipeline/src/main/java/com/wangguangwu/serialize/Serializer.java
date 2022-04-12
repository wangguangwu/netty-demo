package com.wangguangwu.serialize;

import com.wangguangwu.serialize.impl.JsonSerializer;

/**
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * get serialize Algorithm
     *
     * @return serialize Algorithm
     */
    byte getSerializerAlgorithm();

    /**
     * java object convert to binary data
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);

    /**
     * binary data convert to java object
     *
     * @param clazz clazz
     * @param bytes binary data
     * @param <T>   <T>
     * @return T
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
