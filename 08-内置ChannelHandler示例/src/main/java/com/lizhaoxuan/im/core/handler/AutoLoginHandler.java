package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 连接后自动登录处理器
 */
@Slf4j
public class AutoLoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 让用户输入账号、密码
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入用户名:");
        String username = reader.readLine();
        System.out.println("请输入密码:");
        String password = reader.readLine();
        // 构建登录请求包
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userName(username)
                .password(password)
                .build();
        ctx.channel().writeAndFlush(loginRequestPacket);
        super.channelActive(ctx);
    }
}
