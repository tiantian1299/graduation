package com.example.office.wx.common.util;

import io.swagger.annotations.ApiModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@ApiModel(discriminator = "sun jie 2019/4/14 0014 0:32", description = "获取容器上下文", value = "SpringContextUtils")
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtils.context = context;
    }


}
