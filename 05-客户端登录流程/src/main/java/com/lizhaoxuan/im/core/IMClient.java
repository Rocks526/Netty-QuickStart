package com.lizhaoxuan.im.core;

import com.lizhaoxuan.im.core.handler.ClientHandler;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import com.lizhaoxuan.im.protocol.serial.impl.FastJsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


/**
 * IM客户端
 * @author lizhaoxuan
 */
@Slf4j
public class IMClient {

    public static void main(String[] args) {
        IMClient imClient = new IMClient();
        imClient.init();
        imClient.start();
    }

    @SneakyThrows
    private void init() {
        // 初始化，加载一下所有的序列化器，让其进行注册
        Class.forName(FastJsonSerializer.class.getName());
        // 初始化，加载一下所有的指令，让其注册
        Class.forName(LoginRequestPacket.class.getName());
        Class.forName(LoginResponsePacket.class.getName());
    }

    private void start() {
        // 1.创建线程模型
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 2.引导类
        Bootstrap bootstrap = new Bootstrap();
        // 3.配置
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        // 4.连接Server
        bootstrap.connect("127.0.0.1", 9090).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                log.info("[IMClient] connect server success!");
            }else {
                log.error("[IMClient] connect server failed!");
            }
        });
    }

}
