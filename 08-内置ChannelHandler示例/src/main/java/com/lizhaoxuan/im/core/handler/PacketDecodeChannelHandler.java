package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.Packet;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义解码器
 */
@Slf4j
public class PacketDecodeChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将客户端发送的 ByteBuf 解码
        ByteBuf byteBuf = (ByteBuf) msg;
        // 解码
        Packet packet = PacketCodeC.decode(byteBuf);
        // 解码后的数据往后传递
        super.channelRead(ctx, packet);
    }
}
