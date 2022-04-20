package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端业务逻辑处理器
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // 存在的问题：多种消息处理逻辑，耦合在一个处理器里，需要进行拆分
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 之前已经解码，此时传入的msg是Packet数据包
        if (msg instanceof LoginRequestPacket){
            // 登录逻辑...
        }else if (msg instanceof MessageRequestPacket){
            // 发送消息逻辑...
        }
        // 未知类型数据包，无法处理
    }
}
