package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.SessionManager;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录响应处理器
 * @author lizhaoxuan
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    // 通过泛型，声明只处理LoginRequestPacket类型的消息，而且msg已经自动转换为LoginRequestPacket
    // 通过SimpleChannelInboundHandler可以将逻辑拆分为多个ChannelHandler
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        // 登录响应的数据包，进行处理
        // 获取登录结果
        if (Boolean.TRUE.equals(loginResponsePacket.getSuccess())){
            log.info("login success, welcome to use IM!");
            // 用户登录成功，设置登录标识
            SessionManager.login(channelHandlerContext.channel());
        }else {
            log.error("login failed, reason = {}!", loginResponsePacket.getErrMsg());
        }
    }

}
