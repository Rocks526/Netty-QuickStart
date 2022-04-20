package com.lizhaoxuan.im.core;

import com.lizhaoxuan.im.core.handler.*;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import com.lizhaoxuan.im.protocol.serial.impl.FastJsonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * IM服务端
 * @author lizhaoxuan
 */
@Slf4j
public class IMServer {

    public static void main(String[] args) {
        IMServer imServer = new IMServer();
        imServer.start();
        imServer.init();
    }

    @SneakyThrows
    private void init() {
        // 初始化，加载一下所有的序列化器，让其进行注册
        Class.forName(FastJsonSerializer.class.getName());
        // 初始化，加载一下所有的指令，让其注册
        Class.forName(LoginRequestPacket.class.getName());
        Class.forName(LoginResponsePacket.class.getName());
        Class.forName(MessageResponsePacket.class.getName());
        Class.forName(MessageRequestPacket.class.getName());
    }

    private void start() {
        // 1.创建线程模型
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 2.引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 3.配置
        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        log.info("[IMServer] server running....");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 解码器
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        // 登录请求处理器
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        // 消息请求处理器
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        // 编码器
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);
        // 4.绑定端口
        serverBootstrap.bind(9090).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                log.info("[IMServer] server start success , port = 9090!");
            }else {
                log.error("[IMServer] server start failed!");
            }
        });
    }


}
