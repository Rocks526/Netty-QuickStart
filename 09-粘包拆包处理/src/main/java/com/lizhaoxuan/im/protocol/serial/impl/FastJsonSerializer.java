package com.lizhaoxuan.im.protocol.serial.impl;

import com.alibaba.fastjson.JSON;
import com.lizhaoxuan.im.protocol.serial.Serializer;
import com.lizhaoxuan.im.protocol.serial.SerializerManager;

import static com.lizhaoxuan.im.protocol.serial.SerializerAlgorithm.JSON_SERIALIZER;

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
