package com.lizhaoxuan.im.core;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录会话
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {

    // 客户端通道
    private Channel channel;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // TODO 其他属性后续扩充

}
