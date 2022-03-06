package com.example.office.wx.aop;

import com.example.office.wx.common.util.R;
import com.example.office.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.example.office.wx.controller.*.*(..)))")
    public void aspect() {

    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获得拦截方法返回的结果
        R r = (R) point.proceed();
        //检查有没有新生成的令牌
        String token = threadLocalToken.getToken();
        if (token != null) {
            //说明生成了新的令牌，把新令牌放到R对象中返回给客户端
            r.put("token", token);
            //清空threadLocal
            threadLocalToken.clear();
        }
        return r;
    }
}
