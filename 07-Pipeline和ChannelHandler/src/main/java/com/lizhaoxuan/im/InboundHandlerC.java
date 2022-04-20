package com.lizhaoxuan.im;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InboundHandlerC extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("[InboundHandlerC] receive msg = {}!", msg);

        // 注意：此处需要写入数据，触发ChannelOutboundHandlerAdapter
        ctx.channel().writeAndFlush(msg);
//        ctx.writeAndFlush(msg); ==> 从当前处理器往后触发，当前配置的顺序会导致ChannelOutboundHandlerAdapter无法触发
    }

}
