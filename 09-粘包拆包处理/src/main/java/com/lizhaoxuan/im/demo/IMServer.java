package com.lizhaoxuan.im.demo;

import com.lizhaoxuan.im.core.handler.LoginRequestHandler;
import com.lizhaoxuan.im.core.handler.MessageRequestHandler;
import com.lizhaoxuan.im.core.handler.PacketDecoder;
import com.lizhaoxuan.im.core.handler.PacketEncoder;
import com.lizhaoxuan.im.protocol.packet.impl.LoginRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.LoginResponsePacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageRequestPacket;
import com.lizhaoxuan.im.protocol.packet.impl.MessageResponsePacket;
import com.lizhaoxuan.im.protocol.serial.impl.FastJsonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * IM服务端
 * @author lizhaoxuan
 */
@Slf4j
public class IMServer {

    public static void main(String[] args) {
        IMServer imServer = new IMServer();
        imServer.init();
        imServer.start();
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
                        // 添加拆包器
                        // 定长拆包器
                        // nioSocketChannel.pipeline().addLast(new FixedLengthFrameDecoder("你好，这是一个粘包测试案例！！!".getBytes(StandardCharsets.UTF_8).length));
                        // 行拆包器 如果没有换行符，server端会一直阻塞，将所有消息拼接，直到遇到换行符或达到最大长度再统一交给后续处理器处理
                        // nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE));
                        // 分隔符拆包器，相当于行拆包器的定制版，支持指定多个分隔符，默认会将分隔符去掉
//                        ByteBuf delimiter = ByteBufAllocator.DEFAULT.ioBuffer();
//                        delimiter.writeBytes("！！！".getBytes(StandardCharsets.UTF_8));
//                        nioSocketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, delimiter));
                        // 根据长度域的拆包器，最常用的拆包器，根据协议里的长度字段进行拆分
                        // 第一个参数代表每个包的最大长度，第二个参数代表长度字段在协议里的偏移量(在IM的自定制协议里，[魔数:4字节] [版本号:1字节] [序列化算法:1字节] [指令:1字节])，第三个参数代表长度字段的字节数(在定制协议里是4位)
                        // nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        // 对长度域的拆包器进行封装，校验一些协议的基础信息
                        nioSocketChannel.pipeline().addLast(new BaseHandler());
                        nioSocketChannel.pipeline().addLast(new MsgPrintHandler());
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
