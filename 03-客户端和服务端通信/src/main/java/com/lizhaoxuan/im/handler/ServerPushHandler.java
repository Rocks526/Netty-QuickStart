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
 *      作用：客户端连接成功后，给客户端主动推送当前服务器版本信息
 */
@Slf4j
public class ServerPushHandler extends ChannelInboundHandlerAdapter {

    private final String serverProductInfo = "IMServer V1.0";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 构建数据
        ByteBuf buff = buildByteBuff(ctx);
        // 写入客户端
        ctx.writeAndFlush(buff);
    }

    private ByteBuf buildByteBuff(ChannelHandlerContext ctx) {
        // 获取ByteBuf
        ByteBuf buf = ctx.channel().alloc().buffer();
        // 写入数据
        buf.writeBytes(serverProductInfo.getBytes(StandardCharsets.UTF_8));
        return buf;
    }
}
