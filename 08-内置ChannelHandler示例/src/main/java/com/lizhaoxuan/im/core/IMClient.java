package com.lizhaoxuan.im.core;

import com.lizhaoxuan.im.core.handler.*;
import com.lizhaoxuan.im.protocol.packet.PacketCodeC;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import com.lizhaoxuan.im.protocol.serial.impl.FastJsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;


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
        Class.forName(MessageResponsePacket.class.getName());
        Class.forName(MessageRequestPacket.class.getName());
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
                        // 解码器
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        // 连接后自动登录处理器
                        nioSocketChannel.pipeline().addLast(new AutoLoginHandler());
                        // 登录响应处理器
                        nioSocketChannel.pipeline().addLast(new LoginResponseHandler());
                        // 消息响应处理器
                        nioSocketChannel.pipeline().addLast(new MessageResponseHandler());
                        // 编码器
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        // 4.连接Server
        bootstrap.connect("127.0.0.1", 9090).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                log.info("[IMClient] connect server success!");
                // 开启后台线程，处理用户输入
                startConsoleThread(channelFuture.channel());
            }else {
                log.error("[IMClient] connect server failed!");
            }
        });
    }

    private void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()){
                // 登录检查
                if (SessionManager.isLogin(channel)){
                    log.info("[IMClient] Please input message send to server:");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();
                    // 构建消息数据包
                    MessageRequestPacket messageRequestPacket = MessageRequestPacket.builder().message(line).build();
                    // 编码
                    ByteBuf byteBuf = PacketCodeC.encode(messageRequestPacket);
                    // 发送给服务端
                    channel.writeAndFlush(byteBuf);
                }
            }
            log.warn("[IMClient] user console thread interrupted...");
        }).start();
    }


}
