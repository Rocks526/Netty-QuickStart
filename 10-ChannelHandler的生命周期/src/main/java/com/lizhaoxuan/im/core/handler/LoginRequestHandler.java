package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

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

        // 存在的问题：无论是登录响应还是消息响应，最终都需要编码传递给客户端，因此可以拆出来一个单独的处理器放到尾部去做

//        // 编码
//        ByteBuf responseBytes = PacketCodeC.encode(loginResponsePacket);
//        // 发送回客户端
//        channelHandlerContext.channel().writeAndFlush(responseBytes);

        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

}
