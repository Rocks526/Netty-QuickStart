package com.lizhaoxuan.im.core;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.lf5.util.StreamUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理器
 * @author lizhaoxuan
 */
@Slf4j
public class SessionManager {

    // 当前登录的用户会话
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    // 检查是否登录
    public static boolean isLogin(Channel channel){
        // 获取登录标识
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return Boolean.TRUE.equals(attr.get());
    }

    // 设置登录标识
    public static void markAsLogin(Channel channel, String userId){
        channel.attr(Attributes.LOGIN).set(Boolean.TRUE);
        channel.attr(Attributes.USER_ID).set(userId);
    }

    // 绑定会话
    public static void bindSession(Session session, String userId){
        log.info("[SessionManager] bind session with user = {}!", userId);
        sessions.put(userId, session);
    }

    // 解除会话
    public static void removeSession(String userId){
        log.info("[SessionManager] remove session with user = {}!", userId);
        if (Objects.nonNull(userId) && !userId.equals("")){
            // 连接可能登录失败就关闭，因此没有会话
            sessions.remove(userId);
        }
    }

    // 获取用户连接
    public static Session getSession(String userId) {
        return sessions.get(userId);
    }
}