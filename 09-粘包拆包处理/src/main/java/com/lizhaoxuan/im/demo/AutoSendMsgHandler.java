package com.lizhaoxuan.im.demo;

import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 连接被激活后，自动往服务端大量发送消息
 */
@Slf4j
public class AutoSendMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 多次发送
//        for (int i=0;i<1000;i++){
//            ByteBuf byteBuf = buildByteBuf(ctx);
//            ctx.channel().writeAndFlush(byteBuf);
//        }

//        // 一次发送大量数据
//        ByteBuf buffer = ctx.alloc().buffer();
//        StringBuilder msg = new StringBuilder();
//        for (int i=0;i<1000;i++){
//            msg.append("你好，这是一个粘包测试案例！！!");
//        }
//        buffer.writeBytes(msg.toString().getBytes(StandardCharsets.UTF_8));
//        ctx.channel().writeAndFlush(buffer);

        // 自定义协议报文 ==> 用于测试长度域拆包器
        LoginRequestPacket packet = LoginRequestPacket.builder().userName("admin").password("admin1234").build();
        for (int i=0;i<1000;i++){
            ctx.channel().writeAndFlush(PacketCodeC.encode(packet));
        }
    }

    private ByteBuf buildByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("你好，这是一个粘包测试案例！！！".getBytes(StandardCharsets.UTF_8));
        // 用于测试行分隔符
//        buffer.writeBytes("你好，这是一个粘包测试案例！！！\n".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }

}
