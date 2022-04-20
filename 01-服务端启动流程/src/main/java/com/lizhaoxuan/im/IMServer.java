package com.lizhaoxuan.im;

import io.netty.bootstrap.ServerBootstrap;
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
        IMServer imServer = new IMServer();
        imServer.start();
    }

    private void start() {
        // 用于处理客户端接入
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用于处理客户端读写IO
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 引导程序
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 配置信息
        serverBootstrap.group(bossGroup, workGroup) // 线程模型
                .channel(NioServerSocketChannel.class)    // IO类型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  // 客户端处理器
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 暂时不做任何处理

                    }
                });
        // 绑定9090端口
        serverBootstrap.bind(9090);
    }



}
