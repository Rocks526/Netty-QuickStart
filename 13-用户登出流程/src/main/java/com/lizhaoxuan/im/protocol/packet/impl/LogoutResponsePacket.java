package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.*;

/**
 * 用户登出响应数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LogoutResponsePacket extends Packet {

    static {
        CommandManager.registerCommand(LOGOUT_RESPONSE, LogoutResponsePacket.class);
    }

    // 登出是否成功
    private boolean success;
    // 失败原因
    private String errMsg;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }

}
