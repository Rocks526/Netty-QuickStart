package com.lizhaoxuan.im.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 消息打印处理器
 */
@Slf4j
public class MsgPrintHandler extends ChannelInboundHandlerAdapter {

    // 客户端发送1000次小数据包时，包被粘在一起，count打印到300+
    // 客户端发送1次特别大的数据包时，包被拆分，count打印到3
    private Long count = 1L;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive msg {} : {}",count ++, ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
    }
}
