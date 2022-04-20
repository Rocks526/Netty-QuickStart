package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.CommandManager;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础处理器，用于拆包，校验魔数、协议版本、序列化算法、指令等
 */
@Slf4j
public class BaseHandler extends LengthFieldBasedFrameDecoder {

    private static final int lengthFieldOffset = 7;
    private static final int lengthFieldLength = 4;

    public BaseHandler() {
        super(Integer.MAX_VALUE, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        log.info("start validate...");
        // 校验魔数
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER){
            // 非IM协议的连接，直接断开
            ctx.channel().close();
            return null;
        }
        // 校验版本
        if (in.getByte(in.readerIndex() + 4) != PacketCodeC.VERSION){
            // 版本信息不对，断开连接
            ctx.channel().close();
            return null;
        }
        // 校验序列化算法
        if (!SerializerManager.containSerializer(in.getByte(in.readerIndex() + 5))){
            // 序列化算法信息不对，断开连接
            ctx.channel().close();
            return null;
        }
        // 校验指令
        if (!CommandManager.containPacket(in.getByte(in.readerIndex() + 6))){
            // 指令信息不对，断开连接
            ctx.channel().close();
            return null;
        }
//        log.info("validate success...");
        // 拆包
        return super.decode(ctx, in);
    }
}
