package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.LOGIN_RESPONSE;

/**
 * 用户登录响应数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {

    static {
        CommandManager.registerCommand(LOGIN_RESPONSE, LoginResponsePacket.class);
    }

    // 登录是否成功
    private Boolean success;
    // 登录失败的原因
    private String errMsg;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }

}
