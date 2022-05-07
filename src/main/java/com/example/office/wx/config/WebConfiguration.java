package com.example.office.wx.config;

import com.example.office.wx.common.util.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    SpringContextUtils getSpringContextUtils() {
        return new SpringContextUtils();
    }

}