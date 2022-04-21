package com.lizhaoxuan.im.protocol.packet.impl;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.Packet;
import lombok.*;

import static com.lizhaoxuan.im.protocol.packet.Command.MESSAGE_REQUEST;

/**
 * 用户发送消息数据包
 * @author lizhaoxuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageRequestPacket extends Packet {

    static {
        CommandManager.registerCommand(MESSAGE_REQUEST, MessageRequestPacket.class);
    }

    // 要私发的用户ID
    private String toUserId;
    // 消息内容
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
