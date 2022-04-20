package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.Packet;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 登录请求处理器
 * @author lizhaoxuan
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    // 客户端连接成功，让用户输入账号密码进行登录
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 1.让用户输入账号、密码
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入用户名:");
        String username = reader.readLine();
        System.out.println("请输入密码:");
        String password = reader.readLine();
        // 2.构建登录请求包
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userName(username)
                .password(password)
                .build();
        // 3.编码
        ByteBuf byteBuf = PacketCodeC.encode(loginRequestPacket);
        // 4.发送给服务端
        ctx.channel().writeAndFlush(byteBuf);
    }

    // 客户端处理登录响应
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1.解码
        Packet packet = PacketCodeC.decode((ByteBuf) msg);
        // 2.打印服务端发送的请求消息
        log.info("received msg = {}!", packet);
        // 3.判断服务端数据包类型
        if (packet instanceof LoginResponsePacket){
            // 登录响应的数据包，进行处理
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            // 获取登录结果
            if (Boolean.TRUE.equals(loginResponsePacket.getSuccess())){
                log.info("login success, welcome to use IM!");
            }else {
                log.error("login failed, reason = {}!", loginResponsePacket.getErrMsg());
            }
        }
        // 未知类型数据包，无法处理
    }
}
