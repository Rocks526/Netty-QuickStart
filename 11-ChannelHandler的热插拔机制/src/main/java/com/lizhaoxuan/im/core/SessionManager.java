package com.lizhaoxuan.im.core;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 会话管理器
 * @author lizhaoxuan
 */
public class SessionManager {

    // 检查是否登录
    public static boolean isLogin(Channel channel){
        // 获取登录标识
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return Boolean.TRUE.equals(attr.get());
    }

    // 设置登录标识
    public static void login(Channel channel, String userId){
        channel.attr(Attributes.LOGIN).set(Boolean.TRUE);
        channel.attr(Attributes.USER_ID).set(userId);
    }

}
