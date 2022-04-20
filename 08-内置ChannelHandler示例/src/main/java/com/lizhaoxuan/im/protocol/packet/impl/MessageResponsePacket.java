package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.MESSAGE_RESPONSE;

/**
 * 用户发送消息响应数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket extends Packet {

    static {
        CommandManager.registerCommand(MESSAGE_RESPONSE, MessageResponsePacket.class);
    }

    // 响应内容
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }

}
