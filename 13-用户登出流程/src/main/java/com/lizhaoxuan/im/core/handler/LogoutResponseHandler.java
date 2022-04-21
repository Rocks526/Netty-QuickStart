package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.impl.LogoutResponsePacket;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登出响应处理器
 * @author lizhaoxuan
 */
@Slf4j
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutResponsePacket logoutResponsePacket) throws Exception {
        // 获取登出结果
        if (Boolean.TRUE.equals(logoutResponsePacket.isSuccess())){
            log.info("logout success, ready to close channel!");
            channelHandlerContext.channel().close().addListener((ChannelFutureListener) listener -> {
               if (listener.isSuccess()){
                   log.info("channel closed success!");
               }else {
                   log.warn("channel closed failed, reason=", listener.cause());
               }
            });
        }else {
            log.warn("logout failed, reason={}!", logoutResponsePacket.getErrMsg());
        }
    }

}
