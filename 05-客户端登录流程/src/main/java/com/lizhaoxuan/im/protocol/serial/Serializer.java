package com.lizhaoxuan.im.protocol.serial;

/**
 * 序列化接口
 * @author lizhaoxuan
 */
public interface Serializer {

    // 序列化算法
    Byte getSerializerAlgorithm();

    // 序列化
    byte[] serializer(Object obj);

    // 反序列化
    <T> T deSerializer(byte[] bytes, Class<T> clazz);

}
