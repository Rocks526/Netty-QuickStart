package com.lizhaoxuan.im.packet.impl;

import com.lizhaoxuan.im.packet.CommandManager;
import com.lizhaoxuan.im.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lizhaoxuan.im.packet.Command.*;

/**
 * 用户登录请求数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
