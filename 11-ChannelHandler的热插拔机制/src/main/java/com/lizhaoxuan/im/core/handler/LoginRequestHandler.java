package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.SessionManager;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 登录请求处理器
 * @author lizhaoxuan
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    // 通过泛型，声明只处理LoginRequestPacket类型的消息，而且msg已经自动转换为LoginRequestPacket
    // 通过SimpleChannelInboundHandler可以将逻辑拆分为多个ChannelHandler
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        // 处理登录逻辑
        // 校验用户名和密码，暂时不做持久化处理，要求账号密码都必须为admin
        boolean success = loginRequestPacket.getUserName().equals("admin") && loginRequestPacket.getPassword().equals("admin1234");
        // 构建登录响应的数据包
        LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                .success(success)
                .errMsg(success ? "Success" : "账号或密码错误!")
                .build();
        if (success){
            // 用户登录成功，生成用户ID
            String userId = UUID.randomUUID().toString();
            loginResponsePacket.setUserId(userId);
            log.info("Channel[{}] User[{}] login success, UserId={}!", channelHandlerContext.channel().id(), loginRequestPacket.getUserName(), userId);
            // 给服务端连接设置标识
            SessionManager.login(channelHandlerContext.channel(), userId);
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

}
