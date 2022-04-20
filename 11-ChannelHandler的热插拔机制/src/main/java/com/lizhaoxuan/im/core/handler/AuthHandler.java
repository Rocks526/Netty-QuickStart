package com.lizhaoxuan.im.core.handler;

import com.lizhaoxuan.im.core.Attributes;
import com.lizhaoxuan.im.core.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录状态处理器
 * @author lizhaoxuan
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionManager.isLogin(ctx.channel())){
            // 用户未登录，断开连接
            log.info("Client[{}] has not login, closed!", ctx.channel().id());
            ctx.channel().close();
            return;
        }
        log.info("Client[{}] has login, user={}!", ctx.channel().id(), ctx.channel().attr(Attributes.USER_ID).get());
        // 此条连接的用户登录状态已经验证完成，因此后续不做验证，移除此处理器
        ctx.pipeline().remove(this);
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionManager.isLogin(ctx.channel())){
            log.info("Client[{}] login status validate success, removed!", ctx.channel().id());
        }else {
            log.info("Client[{}] has not login, closed and remove handler!", ctx.channel().id());
        }
        super.handlerRemoved(ctx);
    }
}
