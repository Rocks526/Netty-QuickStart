package com.lizhaoxuan.im.protocol.packet;

import com.lizhaoxuan.im.protocol.serial.Serializer;
import com.lizhaoxuan.im.protocol.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.lizhaoxuan.im.protocol.serial.SerializerAlgorithm.JSON_SERIALIZER;

/**
 * 编解码工具
 * @author lizhaoxuan
 */
public class PacketCodeC {

    /**
     * 自定义协议：
     *      [魔数:4字节] [版本号:1字节] [序列化算法:1字节] [指令:1字节] [数据长度:4字节] [数据:N字节]
     */

    // 魔数
    private static final int MAGIC_NUMBER = 0x12345678;

    // 编码
    public static ByteBuf encode(Packet packet){
        // 创建ByteBuf
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 序列化Java对象
        Serializer serializer = SerializerManager.getSerializer(JSON_SERIALIZER);
        byte[] bytes = serializer.serializer(packet);

        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(JSON_SERIALIZER);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    // 编码
    public static ByteBuf encode(Packet packet, Byte serializerAlgorithm){
        // 创建ByteBuf
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 序列化Java对象
        Serializer serializer = SerializerManager.getSerializer(serializerAlgorithm);
        byte[] bytes = serializer.serializer(packet);

        // 编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(serializerAlgorithm);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    // 解码
    public static Packet decode(ByteBuf byteBuf){
        // 魔数、版本、指令、序列化算法的合法性都在后面专门配置handler进行校验
        // 此处不做处理 (抛出异常会导致后面所有处理器都得处理异常)

        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializerAlgorithm = byteBuf.readByte();
        Serializer serializer = SerializerManager.getSerializer(serializerAlgorithm);

        // 指令
        byte command = byteBuf.readByte();
        Class<? extends Packet> packet = CommandManager.getPacket(command);

        // 数据包长度
        int length = byteBuf.readInt();

        // 读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 解码
        return serializer.deSerializer(bytes, packet);
    }

    // 数据包合法性校验
    public static boolean validate(ByteBuf byteBuf){
        // 魔数校验
        int magicNumber = byteBuf.readInt();
        return MAGIC_NUMBER == magicNumber;
    }

}
