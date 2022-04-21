package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.Attributes;
import com.lizhaoxuan.im.core.Session;
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
        // 校验用户名和密码，暂时不做持久化处理，要求密码都必须为admin1234
        boolean success = loginRequestPacket.getPassword().equals("admin1234");
        // 构建登录响应的数据包
        LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                .success(success)
                .errMsg(success ? "Success" : "账号或密码错误!")
                .build();
        if (success){
            // 用户登录成功，生成用户ID，由于太长命令行不好操作，因此截取前四位
            String userId = UUID.randomUUID().toString().substring(0, 4);
            loginResponsePacket.setUserId(userId);
            log.info("Channel[{}] User[{}] login success, UserId={}!", channelHandlerContext.channel().id(), loginRequestPacket.getUserName(), userId);
            // 给服务端连接设置标识
            Session session = Session.builder()
                    .channel(channelHandlerContext.channel())
                    .userName(loginRequestPacket.getUserName())
                    .password(loginRequestPacket.getPassword())
                    .build();
            SessionManager.markAsLogin(channelHandlerContext.channel(), userId);
            SessionManager.bindSession(session, userId);
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel[{}] closed, remove session!", ctx.channel().id());
        SessionManager.removeSession(ctx.channel().attr(Attributes.USER_ID).get());
        super.channelInactive(ctx);
    }
}
