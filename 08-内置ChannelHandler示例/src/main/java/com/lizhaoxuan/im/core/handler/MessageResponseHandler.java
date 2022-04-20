package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息响应处理器
 * @author lizhaoxuan
 */
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        // 服务端响应消息
        log.info("Server response msg={}!", messageResponsePacket.getMessage());
    }

}
