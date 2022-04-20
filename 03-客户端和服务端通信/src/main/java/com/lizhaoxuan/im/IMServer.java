package com.lizhaoxuan.im;

import com.lizhaoxuan.im.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
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
                .handler(new ChannelInitializer<NioServerSocketChannel>() {     // 服务端处理器
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        // 指定服务端启动过程中的一些处理逻辑，一般不用
                        log.info("[IMServer] server is running...");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  // 客户端处理器
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 添加一个处理器，用于接收客户端发来的消息，给客户端回复当前时间
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                    }
                })
                .attr(AttributeKey.newInstance("serverName"), "IMServer")   // 用于给服务端Channel绑定一些属性，通过channel.attr()取出属性，一般不用
                .childAttr(AttributeKey.newInstance("clientName"), "IMClient")   // 同上，不过是绑定给每个客户端的Channel
                .option(ChannelOption.SO_BACKLOG, 1024) // 用于给服务端TCP连接设置一些属性
                .childOption(ChannelOption.SO_KEEPALIVE, true)      // 用于给客户端TCP连接设置一些属性
                .childOption(ChannelOption.TCP_NODELAY, true);

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
