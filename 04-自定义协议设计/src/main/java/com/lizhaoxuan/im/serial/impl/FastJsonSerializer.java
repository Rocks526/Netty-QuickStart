package com.lizhaoxuan.im.serial.impl;

import com.alibaba.fastjson.JSON;
import com.lizhaoxuan.im.serial.Serializer;
import com.lizhaoxuan.im.serial.SerializerManager;

import static com.lizhaoxuan.im.serial.SerializerAlgorithm.*;

/**
 * fastjson序列化
 */
public class FastJsonSerializer implements Serializer {

    static {
        SerializerManager.registerSerializer(JSON_SERIALIZER, new FastJsonSerializer());
    }

    @Override
    public Byte getSerializerAlgorithm() {
        return JSON_SERIALIZER;
    }

    @Override
    public byte[] serializer(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deSerializer(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

}
