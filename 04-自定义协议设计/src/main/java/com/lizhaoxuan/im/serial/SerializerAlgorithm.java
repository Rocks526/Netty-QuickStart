package com.lizhaoxuan.im.serial;

/**
 * 序列化算法
 * @author lizhaoxuan
 */
public interface SerializerAlgorithm {

    // JSON序列化
    Byte JSON_SERIALIZER = 1;

    // Java原生序列化
    Byte JAVA_SERIALIZER = 2;

    // Protobuf序列化
    Byte PROTOBUF_SERIALIZER = 3;

}
