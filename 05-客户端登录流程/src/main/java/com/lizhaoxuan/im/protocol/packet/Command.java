package com.lizhaoxuan.im.protocol.packet;

/**
 * 所有支持的指令
 * @author lizhaoxuan
 */
public interface Command {

    // 登录请求
    Byte LOGIN_REQUEST = 1;

    // 登录响应
    Byte LOGIN_RESPONSE = 2;

}
