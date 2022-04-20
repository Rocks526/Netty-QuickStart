package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.*;
import static com.lizhaoxuan.im.protocol.packet.Command.LOGIN_REQUEST;

/**
 * 用户登录请求数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    static {
        CommandManager.registerCommand(LOGIN_REQUEST, LoginRequestPacket.class);
    }

    // 用户名
    private String userName;
    // 密码
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

}
