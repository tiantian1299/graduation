package com.example.office.wx.config.shiro;

import org.springframework.stereotype.Component;

/**
 * 存储新生成的令牌
 */
@Component
public class ThreadLocalToken {
    private ThreadLocal<String> local = new ThreadLocal<>();

    public void setToken(String token) {
        local.set(token);
    }

    public String getToken(){
        return local.get();
    }

    public void clear(){
        local.remove();
    }
}
