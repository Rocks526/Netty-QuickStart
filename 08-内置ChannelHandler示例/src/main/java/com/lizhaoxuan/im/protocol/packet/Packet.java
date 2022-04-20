package com.lizhaoxuan.im.protocol.packet;

import lombok.Data;

/**
 * 消息数据库的Java对象映射
 * @author lizhaoxuan
 */
@Data
public abstract class Packet {

    // 版本号
    private Byte version = 1;

    // 指令
    public abstract Byte getCommand();

}
