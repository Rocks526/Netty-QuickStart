package com.lizhaoxuan.im.serial;

import com.alibaba.fastjson.JSON;
import com.lizhaoxuan.im.serial.impl.FastJsonSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
