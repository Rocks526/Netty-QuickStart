package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.Attributes;
import com.lizhaoxuan.im.core.Session;
import com.lizhaoxuan.im.core.SessionManager;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 消息请求处理器
 * @author lizhaoxuan
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        // 处理用户发送的消息
        log.info("Client = {}, send Msg={}!", channelHandlerContext.channel().id(), messageRequestPacket);
        // 构建服务端返回数据包
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .message(messageRequestPacket.getMessage())
                .build();
        Session session = SessionManager.getSession(messageRequestPacket.getToUserId());
        if (Objects.nonNull(session)){
            // 目标用户在线
            Session fromSession = SessionManager.getSession(channelHandlerContext.channel().attr(Attributes.USER_ID).get());
            messageResponsePacket.setFromUser(fromSession.getUserName());
            messageResponsePacket.setFromUserId(channelHandlerContext.channel().attr(Attributes.USER_ID).get());
            session.getChannel().writeAndFlush(messageResponsePacket);
            return;
        }
        // 目标用户未上线
        MessageResponsePacket messageResponsePacket2 = MessageResponsePacket.builder()
                .fromUserId("IM Server")
                .fromUser("系统提示")
                .message("用户[" + messageRequestPacket.getToUserId() + "]未上线!")
                .build();
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket2);
    }

}
