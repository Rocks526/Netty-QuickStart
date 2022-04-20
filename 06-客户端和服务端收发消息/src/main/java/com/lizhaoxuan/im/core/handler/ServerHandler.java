package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.Packet;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


/**
 * 登录响应处理器
 * @author lizhaoxuan
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // 用于处理客户端登录请求
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1.解码
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.decode(byteBuf);
        // 2.打印客户端发送的请求消息
//        log.info("received msg = {}!", packet);
        // 3.判断数据包类型
        if (packet instanceof LoginRequestPacket){
            // 登录请求
            // 校验用户名和密码，暂时不做持久化处理，要求账号密码都必须为admin
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            boolean success = loginRequestPacket.getUserName().equals("admin") && loginRequestPacket.getPassword().equals("admin1234");
            log.info("Client = {}, login = {}!", ctx.channel().id(), success);
            // 构建登录响应的数据包
            LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                    .success(success)
                    .errMsg(success ? "Success" : "账号或密码错误!")
                    .build();
            // 编码
            ByteBuf responseBytes = PacketCodeC.encode(loginResponsePacket);
            // 发送回客户端
            ctx.channel().writeAndFlush(responseBytes);
        }else if (packet instanceof MessageRequestPacket){
            // 处理用户发送的消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            String message = messageRequestPacket.getMessage();
            log.info("Client = {}, send Msg={}!", ctx.channel().id(), message);
            // 构建服务端返回数据包
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder().message("ACK -> " + message).build();
            // 编码
            ByteBuf responseBytes = PacketCodeC.encode(messageResponsePacket);
            // 发送给客户端
            ctx.channel().writeAndFlush(responseBytes);
        }
        // 非登录请求，暂时不做处理
    }
}
