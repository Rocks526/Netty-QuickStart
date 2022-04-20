package com.lizhaoxuan.im;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * IM服务端
 * @author lizhaoxuan
 */
@Slf4j
public class IMServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new InboundHandlerA());
                        nioSocketChannel.pipeline().addLast(new InboundHandlerB());
                        nioSocketChannel.pipeline().addLast(new InboundHandlerC());

                        nioSocketChannel.pipeline().addLast(new OutboundHandlerA());
                        nioSocketChannel.pipeline().addLast(new OutboundHandlerB());
                        nioSocketChannel.pipeline().addLast(new OutboundHandlerC());
                    }
                });
        serverBootstrap.bind(9090).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                log.info("[IMServer] start success with port 9090!");
            }else {
                log.error("[IMServer] start failed!");
            }
        });
    }

}
