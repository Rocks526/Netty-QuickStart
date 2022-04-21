package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.*;

/**
 * 用户登出请求数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LogoutRequestPacket extends Packet {

    static {
        CommandManager.registerCommand(LOGOUT_REQUEST, LogoutRequestPacket.class);
    }

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }

}
