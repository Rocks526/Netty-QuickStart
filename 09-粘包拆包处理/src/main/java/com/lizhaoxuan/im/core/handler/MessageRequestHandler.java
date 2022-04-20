package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息请求处理器
 * @author lizhaoxuan
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        // 处理用户发送的消息
        String message = messageRequestPacket.getMessage();
        log.info("Client = {}, send Msg={}!", channelHandlerContext.channel().id(), message);
        // 构建服务端返回数据包
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder().message("ACK -> " + message).build();
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }

}
