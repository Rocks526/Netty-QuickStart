package com.lizhaoxuan.im.serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.lizhaoxuan.im.serial.SerializerAlgorithm.*;

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
        return serializerMaps.getOrDefault(serializerAlgorithm, serializerMaps.get(JSON_SERIALIZER));
    }

}
