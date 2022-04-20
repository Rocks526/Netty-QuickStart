package com.lizhaoxuan.im;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *  IM客户端
 * @author lizhaoxuan
 */
@Slf4j
public class IMClient {

    public static void main(String[] args) {
        IMClient imClient = new IMClient();
        imClient.start();
    }

    private void start() {
        // 工作线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 启动类
        Bootstrap bootstrap = new Bootstrap();
        // 配置
        bootstrap.group(workGroup)  // 线程模型
            .channel(NioSocketChannel.class)    // IO模型
            .handler(new ChannelInitializer<SocketChannel>() {      // IO处理逻辑
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 暂时不做处理
                }
            });
        // 连接
        bootstrap.connect("127.0.0.1", 9090).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("[IMClient] connect server success!");
                    return;
                }
                log.warn("[IMClient] connect server failed!");
            }
        });
    }


}
