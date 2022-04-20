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
 * IM服务端   实现自动递增绑定端口
 * @author lizhaoxuan
 */
@Slf4j
public class IMServer2 {

    public static void main(String[] args) {
        IMServer2 imServer = new IMServer2();
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
        // 从9090开始，自动递增绑定端口
        bind(serverBootstrap, 9090);
    }

    // 抽出单独方法，用于实现自动绑定自增端口
    private void bind(ServerBootstrap serverBootstrap, int port){
        serverBootstrap.bind(port).addListener(new ChannelFutureListener() {
            @Override
            // 绑定完后的回调
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("[IMServer] start with port [{}] success!", port);
                    return;
                }
                // 绑定失败，重新尝试
                log.warn("[IMServer] port [{}] is used, try next port [{}]!", port, port + 1);
                bind(serverBootstrap, port + 1);
            }
        });
    }


}
