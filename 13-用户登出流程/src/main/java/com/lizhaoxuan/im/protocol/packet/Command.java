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

    // 发送消息请求
    Byte MESSAGE_REQUEST = 3;

    // 发送消息响应
    Byte MESSAGE_RESPONSE = 4;

    // 登出请求
    Byte LOGOUT_REQUEST = 5;

    // 登出响应
    Byte LOGOUT_RESPONSE = 6;

}
