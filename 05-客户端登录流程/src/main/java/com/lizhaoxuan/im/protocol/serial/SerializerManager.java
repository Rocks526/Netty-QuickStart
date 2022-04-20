package com.lizhaoxuan.im.protocol.serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.lizhaoxuan.im.protocol.serial.SerializerAlgorithm.*;
import static com.lizhaoxuan.im.protocol.serial.SerializerAlgorithm.JSON_SERIALIZER;

/**
 * 序列化器管理类
 * @author lizhaoxuan
 */
public class SerializerManager {

    // 序列化算法和序列化器的映射
    private static final Map<Byte, Serializer> serializerMaps = new ConcurrentHashMap<>();

    public static void registerSerializer(Byte serializerAlgorithm, Serializer serializer){
        serializerMaps.put(serializerAlgorithm, serializer);
    }

    public static Serializer getSerializer(Byte serializerAlgorithm){
        return serializerMaps.get(serializerAlgorithm);
    }

}
