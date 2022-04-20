package com.lizhaoxuan.im.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Server端IO处理器
 *      作用：返回给客户端当前的时间
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // 接收到客户端数据时触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 打印客户端发送的消息
        log.info("[IMServer] receive client[{}] msg={}!", ctx.channel().id(), ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
        // 准备数据
        ByteBuf buffer = getByteBuf(ctx, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 写入客户端
        ctx.writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String content) {
        // 获取ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 写入数据
        buffer.writeBytes(content.getBytes(StandardCharsets.UTF_8));
        return buffer;
    }


}
