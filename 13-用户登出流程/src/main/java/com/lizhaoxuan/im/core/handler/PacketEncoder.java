package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.Packet;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        // 只用做最核心的编码处理，ByteBuf 不需要手动创建，也不用显式传递给下一个处理器
        PacketCodeC.encode(packet, byteBuf);
    }
}
