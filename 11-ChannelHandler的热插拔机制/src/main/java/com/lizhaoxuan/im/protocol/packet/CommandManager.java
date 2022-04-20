package com.lizhaoxuan.im.protocol.packet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 指令管理器
 * @author lizhaoxuan
 */
public class CommandManager {

    // 指令和数据包映射
    private final static Map<Byte,Class<? extends Packet>> commandMaps = new ConcurrentHashMap<>();

    // 注册指令
    public static void registerCommand(Byte command, Class<? extends Packet> packet){
        commandMaps.put(command, packet);
    }

    // 获取指令对应的数据包对象
    public static Class<? extends Packet> getPacket(Byte command){
        return commandMaps.get(command);
    }

    // 判断指令是否合法
    public static boolean containPacket(Byte command){
        return commandMaps.containsKey(command);
    }

}
