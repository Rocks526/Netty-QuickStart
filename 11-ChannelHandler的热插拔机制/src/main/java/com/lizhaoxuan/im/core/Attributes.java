package com.lizhaoxuan.im.core;

import io.netty.util.AttributeKey;

/**
 * 通道上设置的标志位
 * @author lizhaoxuan
 */
public interface Attributes {

    // 登录标识
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("IM_LOGIN");

    // 用户ID
    AttributeKey<String> USER_ID = AttributeKey.newInstance("IM_USER_ID");

}
