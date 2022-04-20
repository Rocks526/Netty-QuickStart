package com.lizhaoxuan.im;

import com.lizhaoxuan.im.handler.ClientHelloHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  IM客户端
 * @author lizhaoxuan
 */
@Slf4j
public class IMClient {

    private static final Integer MAX_RETRY_COUNT = 5;

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
                    // 添加一个处理器，用于连接到服务端后，给服务端发送hello
                    socketChannel.pipeline().addLast(new ClientHelloHandler());
                }
            })
            .attr(AttributeKey.newInstance("clientName"), "IMClient")   // 为客户端连接绑定一个属性
            .option(ChannelOption.SO_KEEPALIVE, true)   // 为客户端TCP连接设置属性
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)     // 5s连接不到算超时
            ;
        // 连接
        connectServer(bootstrap, "127.0.0.1", 9090, 1);
    }

    private void connectServer(Bootstrap bootstrap, String serverIp, int serverPort, int retry){
        bootstrap.connect(serverIp, serverPort).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                // 连接成功
                log.info("[IMClient] connect server success!");
                return;
            }
            if (retry >= MAX_RETRY_COUNT){
                // 重试结束
                log.error("[IMClient] connect server failed, retry finished!");
                return;
            }
            // 计算等待时间，幂等尝试 1 -> 2 -> 4 -> 8 -> 16
            int delay = 1 << retry;
            log.warn("[IMClient] retry connect server, retry count={}, delay={}!", retry, delay);
            // 通过配置的work线程定时重连
            bootstrap.config().group().schedule(() -> connectServer(bootstrap,serverIp,serverPort,retry+1)
            , delay, TimeUnit.SECONDS);
        });
    }


}
