package com.lizhaoxuan.im.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;

/**
 * 客户端IO处理器
 *      作用：连接到服务端后，给服务端发送hello
 */
@Slf4j
public class ClientHelloHandler extends ChannelInboundHandlerAdapter {

    // 客户端连接到服务端，channel被激活时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 生成要写入的数据
        ByteBuf byteBuf = getByteBuf(ctx, "Hello, Please send current time to me!");
        // 给服务端写入数据
        ctx.channel().writeAndFlush(byteBuf);
        log.info("[IMClient] send msg to server success!");
    }

    // 收到服务端返回的消息时触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("[IMClient] receive server response, msg={}!", ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String content) {
        // 获取ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 准备数据
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        // 写入数据
        buffer.writeBytes(bytes);
        return buffer;
    }

}
