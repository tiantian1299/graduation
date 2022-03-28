package com.example.office.wx;

import cn.hutool.core.util.StrUtil;
import com.example.office.wx.config.SystemConstants;
import com.example.office.wx.db.mapper.SysConfigMapper;
import com.example.office.wx.db.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootApplication
@ServletComponentScan
//开启异步多线程
@EnableAsync
@Slf4j
public class OnlineOfficeApplication {

    @Autowired
    SysConfigMapper sysConfigMapper;

    @Autowired
    SystemConstants constants;


    public static void main(String[] args) {
        SpringApplication.run(OnlineOfficeApplication.class, args);
    }

    @PostConstruct
    public void init(){
        List<SysConfig> sysConfigs = sysConfigMapper.selectAllParam();
        for (SysConfig config : sysConfigs){
            String key = config.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value =config.getParamValue();
            try {
                Field field = constants.getClass().getDeclaredField(key);
                field.set(constants,value);
            } catch (Exception e) {
                log.error("处理异常");
            }
        }
    }
}
