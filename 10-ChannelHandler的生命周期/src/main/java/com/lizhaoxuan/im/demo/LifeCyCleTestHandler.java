package com.lizhaoxuan.im.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * ChannelHandler生命周期测试
 */
@Slf4j
public class LifeCyCleTestHandler extends ChannelInboundHandlerAdapter {

    public LifeCyCleTestHandler() {
        super();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("处理器被添加...");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("处理器被移除...");
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("通道[{}]注册到事件循环...", ctx.channel().id());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("通道[{}]从事件循环中解除注册...", ctx.channel().id());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道[{}]连接成功...", ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道[{}]连接断开...", ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("通道[{}]发送消息 = {}!", ctx.channel().id(), ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("通道[{}]发送的消息已被接收..", ctx.channel().id());
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("通道[{}]处理过程中出现异常，异常原因={}!", ctx.channel().id(), cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean isSharable() {
        log.info("当前通道是否是单例共享的={}!", super.isSharable());
        return super.isSharable();
    }

}
