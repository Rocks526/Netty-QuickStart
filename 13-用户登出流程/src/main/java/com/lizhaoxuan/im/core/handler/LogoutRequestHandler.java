package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.Attributes;
import com.lizhaoxuan.im.core.SessionManager;
import com.lizhaoxuan.im.protocol.packet.impl.LogoutRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LogoutResponsePacket;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 用户登出请求处理器
 * @author lizhaoxuan
 */
@Slf4j
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutRequestPacket logoutRequestPacket) throws Exception {
        // 通过随机数生成成功或失败
        Random random = new Random();
        boolean success = random.nextBoolean();
        LogoutResponsePacket logoutResponsePacket = LogoutResponsePacket.builder().success(success).errMsg(success ? "" : "系统繁忙,请稍后重试...").build();
        if (success){
            // 用户登出，清理用户会话
            log.info("Client[{}] start logout, userId={}!", channelHandlerContext.channel().id(), channelHandlerContext.channel().attr(Attributes.USER_ID).get());
            SessionManager.removeSession(channelHandlerContext.channel().attr(Attributes.USER_ID).get());
            log.info("Client[{}] remove session success, userId={}!", channelHandlerContext.channel().id(), channelHandlerContext.channel().attr(Attributes.USER_ID).get());
        }
        channelHandlerContext.channel().writeAndFlush(logoutResponsePacket);
    }

}
