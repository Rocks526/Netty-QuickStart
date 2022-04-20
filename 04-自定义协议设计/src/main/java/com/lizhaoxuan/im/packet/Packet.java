package com.lizhaoxuan.im.packet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息数据库的Java对象映射
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public abstract class Packet {

    // 版本号
    private Byte version = 1;

    // 指令
    public abstract Byte getCommand();

}
