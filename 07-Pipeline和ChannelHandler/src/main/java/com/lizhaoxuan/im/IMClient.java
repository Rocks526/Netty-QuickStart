package com.lizhaoxuan.im;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * IM客户端
 * @author lizhaoxuan
 */
@Slf4j
public class IMClient {

    public static void main(String[] args) {
        NioEventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                    }
                });
        bootstrap.connect("127.0.0.1", 9090).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                log.info("[IMClient] connect to server success!");
                // 开启后台线程，处理用户输入
                startConsoleThread(channelFuture.channel());
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()){
                log.info("[IMClient] Please input message send to server:");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                ByteBuf byteBuf = channel.alloc().buffer();
                byteBuf.writeBytes(line.getBytes(StandardCharsets.UTF_8));
                channel.writeAndFlush(byteBuf);
            }
            log.warn("[IMClient] user console thread interrupted...");
        }).start();
    }


}
