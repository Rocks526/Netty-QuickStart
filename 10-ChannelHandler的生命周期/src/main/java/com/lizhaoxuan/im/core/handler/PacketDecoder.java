package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 只需要写入解码逻辑，会自动往后传递，并且释放 ByteBuf 内存
        // Netty 4.1.6.Final 版本，默认使用堆外内存，之前示例里都没有释放内存，会导致内存泄漏
        list.add(PacketCodeC.decode(byteBuf));
    }
}
